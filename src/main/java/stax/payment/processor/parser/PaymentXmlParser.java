package stax.payment.processor.parser;


import stax.payment.processor.model.Payment;

import java.io.InputStream;
import java.util.function.Consumer;

public interface PaymentXmlParser {

    void parse(
            InputStream inputStream,
            Consumer<Payment> paymentConsumer
    ) throws Exception;
}
