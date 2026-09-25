package stax.payment.processor.parser;

import org.springframework.stereotype.Component;
import stax.payment.processor.model.Payment;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Component
public class PaymentXmlWriter {

    public File write(List<Payment> payments) throws Exception {

        File outputFile = new File("processed-payments.xml");

        XMLOutputFactory factory = XMLOutputFactory.newFactory();

        try (FileOutputStream outputStream =
                     new FileOutputStream(outputFile)) {

            XMLStreamWriter writer =
                    factory.createXMLStreamWriter(outputStream, "UTF-8");

            writer.writeStartDocument("UTF-8", "1.0");

            writer.writeStartElement("payments");

            for (Payment payment : payments) {

                writer.writeStartElement("payment");

                writeElement(
                        writer,
                        "paymentId",
                        payment.getPaymentId()
                );

                writer.writeStartElement("debtor");

                if (payment.getDebtor() != null) {
                    writeElement(
                            writer,
                            "name",
                            payment.getDebtor().getName()
                    );

                    writeElement(
                            writer,
                            "accountNumber",
                            payment.getDebtor().getAccountNumber()
                    );

                    writeElement(
                            writer,
                            "bank",
                            payment.getDebtor().getBank()
                    );
                }

                writer.writeEndElement();

                writer.writeStartElement("creditor");

                if (payment.getCreditor() != null) {
                    writeElement(
                            writer,
                            "name",
                            payment.getCreditor().getName()
                    );

                    writeElement(
                            writer,
                            "accountNumber",
                            payment.getCreditor().getAccountNumber()
                    );

                    writeElement(
                            writer,
                            "bank",
                            payment.getCreditor().getBank()
                    );
                }

                writer.writeEndElement();

                writeElement(
                        writer,
                        "amount",
                        payment.getAmount()
                );

                writeElement(
                        writer,
                        "currency",
                        payment.getCurrency()
                );

                writer.writeEndElement(); // payment
            }

            writer.writeEndElement(); // payments

            writer.writeEndDocument();
            writer.flush();
            writer.close();
        }

        return outputFile;
    }

    private void writeElement(
            XMLStreamWriter writer,
            String name,
            Object value) throws Exception {

        writer.writeStartElement(name);

        if (value != null) {
            writer.writeCharacters(value.toString());
        }

        writer.writeEndElement();
    }
}