package stax.payment.processor.service;

import org.springframework.stereotype.Service;
import stax.payment.processor.parser.PaymentXmlParser;
import stax.payment.processor.repository.PaymentRepository;
import stax.payment.processor.validator.PaymentValidator;

import java.io.InputStream;

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
        return null;
    }

    public ProcessingResult processFile(InputStream inputStream)
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
