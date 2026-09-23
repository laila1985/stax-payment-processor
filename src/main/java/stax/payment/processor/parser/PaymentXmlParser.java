package com.example.payment.parser;

import com.example.payment.model.Payment;

import java.io.InputStream;
import java.util.function.Consumer;

public interface PaymentXmlParser {

    void parse(
            InputStream inputStream,
            Consumer<Payment> paymentConsumer
    ) throws Exception;
}
