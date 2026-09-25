package stax.payment.processor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import stax.payment.processor.model.Payment;
import stax.payment.processor.parser.PaymentXmlWriter;
import stax.payment.processor.parser.StaxPaymentXmlParser;
import stax.payment.processor.repository.PaymentRepository;
import stax.payment.processor.validator.PaymentValidator;
import stax.payment.processor.validator.XmlValidator;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;

@Service
public class PaymentFileService {

    private static final Logger log =
            LoggerFactory.getLogger(PaymentFileService.class);

    private final StaxPaymentXmlParser parser;
    private final PaymentValidator validator;
    private final PaymentRepository repository;
    private final XmlValidator xmlValidator;
    private final PaymentXmlWriter writer;

    public PaymentFileService(
            StaxPaymentXmlParser parser,
            PaymentValidator validator,
            PaymentRepository repository,
            XmlValidator xmlValidator,
            PaymentXmlWriter writer) {

        this.parser = parser;
        this.validator = validator;
        this.repository = repository;
        this.xmlValidator = xmlValidator;
        this.writer = writer;
    }

    public FileSystemResource process(InputStream inputStream) {

        log.info("Starting payment file processing");

        try {

            // Read the input once so that we can use it
            // for validation and parsing.
            byte[] xmlContent = inputStream.readAllBytes();

            log.info("Payment XML file loaded. Size: {} bytes",
                    xmlContent.length);

            // -------------------------------------------------
            // 1. Validate XML format
            // -------------------------------------------------

            log.info("Starting XML validation");

            xmlValidator.validate(
                    new ByteArrayInputStream(xmlContent)
            );

            log.info("XML validation completed successfully");

            // -------------------------------------------------
            // 2. Parse XML using StAX
            // -------------------------------------------------

            log.info("Starting StAX payment parsing");

            List<Payment> payments = parser.parse(
                    new ByteArrayInputStream(xmlContent)
            );

            log.info(
                    "StAX parsing completed. {} payments found",
                    payments.size()
            );

            // -------------------------------------------------
            // 3. Validate payment data
            // -------------------------------------------------

            log.info("Starting payment business validation");

            for (Payment payment : payments) {

                log.debug(
                        "Validating payment: {}",
                        payment.getPaymentId()
                );

                validator.validate(payment);
            }

            log.info(
                    "Payment validation completed successfully. {} payments validated",
                    payments.size()
            );

            // -------------------------------------------------
            // 4. Save payments
            // -------------------------------------------------

            log.info(
                    "Saving {} payments to database",
                    payments.size()
            );

            repository.saveAll(payments);

            log.info(
                    "Successfully saved {} payments",
                    payments.size()
            );

            // -------------------------------------------------
            // 5. Generate processed XML
            // -------------------------------------------------

            log.info("Generating processed payment XML");

            File outputFile = writer.write(payments);

            log.info(
                    "Processed payment XML generated: {}",
                    outputFile.getAbsolutePath()
            );

            log.info("Payment file processing completed successfully");

            return new FileSystemResource(outputFile);

        } catch (Exception e) {

            log.error(
                    "Payment file processing failed",
                    e
            );

            throw new RuntimeException(
                    "Payment file processing failed",
                    e
            );
        }
    }
}