package stax.payment.processor.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import stax.payment.processor.exceptions.PaymentParsingException;
import stax.payment.processor.model.Party;
import stax.payment.processor.model.Payment;
import stax.payment.processor.service.PaymentFileService;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class StaxPaymentXmlParser {

    private static final Logger log =
            LoggerFactory.getLogger(StaxPaymentXmlParser.class);

    public List<Payment> parse(InputStream inputStream)
            throws PaymentParsingException {

        List<Payment> payments = new ArrayList<>();

        XMLInputFactory factory = XMLInputFactory.newFactory();

        // Security: do not allow DTD/external entity processing
        factory.setProperty(
                XMLInputFactory.SUPPORT_DTD,
                false
        );

        factory.setProperty(
                "javax.xml.stream.isSupportingExternalEntities",
                false
        );

        XMLStreamReader reader = null;

        try {

            reader = factory.createXMLStreamReader(inputStream);

            Payment payment = null;
            Party debtor = null;
            Party creditor = null;

            while (reader.hasNext()) {

                int event = reader.next();

                if (event == XMLStreamConstants.START_ELEMENT) {

                    String element = reader.getLocalName();

                    switch (element) {

                        case "payment":
                            payment = new Payment();
                            break;

                        case "debtor":
                            debtor = new Party();
                            break;

                        case "creditor":
                            creditor = new Party();
                            break;

                        case "paymentId":
                            if (payment != null) {
                                payment.setPaymentId(
                                        reader.getElementText()
                                );
                            }
                            break;

                        case "name":
                            String name = reader.getElementText();

                            if (debtor != null) {
                                debtor.setName(name);
                            } else if (creditor != null) {
                                creditor.setName(name);
                            }
                            break;

                        case "accountNumber":
                            String accountNumber =
                                    reader.getElementText();

                            if (debtor != null) {
                                debtor.setAccountNumber(accountNumber);
                            } else if (creditor != null) {
                                creditor.setAccountNumber(accountNumber);
                            }
                            break;

                        case "bank":
                            String bank = reader.getElementText();

                            if (debtor != null) {
                                debtor.setBank(bank);
                            } else if (creditor != null) {
                                creditor.setBank(bank);
                            }
                            break;

                        case "amount":
                            if (payment != null) {
                                payment.setAmount(
                                        new BigDecimal(
                                                reader.getElementText()
                                        )
                                );
                            }
                            break;

                        case "currency":
                            if (payment != null) {
                                payment.setCurrency(
                                        reader.getElementText()
                                );
                            }
                            break;

                        default:
                            // Ignore unknown elements for now
                            break;
                    }
                }

                if (event == XMLStreamConstants.END_ELEMENT) {

                    String element = reader.getLocalName();

                    switch (element) {

                        case "debtor":
                            if (payment != null) {
                                payment.setDebtor(debtor);
                            }
                            debtor = null;
                            break;

                        case "creditor":
                            if (payment != null) {
                                payment.setCreditor(creditor);
                            }
                            creditor = null;
                            break;

                        case "payment":
                            if (payment != null) {
                                payments.add(payment);
                            }
                            payment = null;
                            break;

                        default:
                            break;
                    }
                }
            }

            return payments;

        } catch (XMLStreamException | NumberFormatException e) {

            throw new PaymentParsingException(
                    "Failed to parse payment XML file",
                    e
            );

        } finally {
            log.info(
                    "StAX parsing completed. {} payments found",
                    payments.size()
            );

            if (reader != null) {
                try {
                    reader.close();
                } catch (XMLStreamException e) {
                    // Parsing already completed/failed.
                    // Nothing else to do here.
                }
            }
        }
    }
}