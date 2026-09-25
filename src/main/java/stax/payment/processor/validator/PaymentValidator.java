package stax.payment.processor.validator;

import org.springframework.stereotype.Component;
import stax.payment.processor.model.Payment;

@Component
public class PaymentValidator {

    public void validate(Payment payment) {

        if (payment.getPaymentId() == null ||
                payment.getPaymentId().isBlank()) {

            throw new IllegalArgumentException(
                    "Payment ID is required"
            );
        }

        if (payment.getAmount() == null ||
                payment.getAmount().signum() <= 0) {

            throw new IllegalArgumentException(
                    "Payment amount must be greater than zero"
            );
        }

        if (payment.getCurrency() == null ||
                payment.getCurrency().isBlank()) {

            throw new IllegalArgumentException(
                    "Currency is required"
            );
        }
    }
}
