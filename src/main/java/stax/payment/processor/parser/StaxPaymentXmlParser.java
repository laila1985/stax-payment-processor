package stax.payment.processor.parser;

import org.springframework.stereotype.Component;
import stax.payment.processor.model.Payment;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.function.Consumer;

@Component
public class StaxPaymentXmlParser
        implements PaymentXmlParser {

    @Override
    public void parse(
            InputStream inputStream,
            Consumer<Payment> paymentConsumer)
            throws Exception {

        XMLInputFactory factory =
                XMLInputFactory.newFactory();

        XMLStreamReader reader =
                factory.createXMLStreamReader(inputStream);

        while (reader.hasNext()) {

            int event = reader.next();

            if (event == XMLStreamConstants.START_ELEMENT
                    && "payment".equals(reader.getLocalName())) {

                Payment payment = readPayment(reader);

                paymentConsumer.accept(payment);
            }
        }

        reader.close();
    }

    private Payment readPayment(XMLStreamReader reader)
            throws XMLStreamException {

        Payment payment = new Payment();

        while (reader.hasNext()) {

            int event = reader.next();

            if (event == XMLStreamConstants.START_ELEMENT) {

                switch (reader.getLocalName()) {

                    case "paymentId":
                        payment.setPaymentId(reader.getElementText());
                        break;

                    case "debtor":
                        payment.setDebtorName(readParty(reader));
                        break;

                    case "creditor":
                        payment.setCreditorName(readParty(reader));
                        break;

                    case "amount":
                        payment.setAmount(
                                new BigDecimal(reader.getElementText())
                        );

                        payment.setCurrency(
                                reader.getAttributeValue(
                                        null,
                                        "currency"
                                )
                        );
                        break;
                }
            }

            if (event == XMLStreamConstants.END_ELEMENT
                    && "payment".equals(reader.getLocalName())) {

                break;
            }
        }

        return payment;
    }

    private String readParty(XMLStreamReader reader) {
        return "party";
    }
}
