package com.example.payment.service;

@Service
public class PaymentFileService {

    private final PaymentXmlParser parser;
    private final PaymentValidator validator;
    private final PaymentRepository repository;

    public PaymentFileService(
            PaymentXmlParser parser,
            PaymentValidator validator,
            PaymentRepository repository) {

        this.parser = parser;
        this.validator = validator;
        this.repository = repository;
    }

    public ProcessingResult process(InputStream inputStream)
            throws Exception {

        ProcessingResult result = new ProcessingResult();

        parser.parse(inputStream, payment -> {

            if (validator.isValid(payment)) {
                repository.save(payment);
                result.incrementProcessed();
            } else {
                result.incrementRejected();
            }
        });

        return result;
    }
}
