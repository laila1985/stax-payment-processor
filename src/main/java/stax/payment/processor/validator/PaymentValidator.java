package stax.payment.processor.validator;

import org.springframework.stereotype.Component;
import stax.payment.processor.model.Payment;

@Component
public class PaymentValidator {

    public boolean isValid(Payment payment) {

        if (payment.getPaymentId() == null) {
            return false;
        }

        if (payment.getAmount() == null ||
                payment.getAmount().signum() <= 0) {

            return false;
        }

        if (payment.getDebtorName() == null) {
            return false;
        }

        if (payment.getCreditorName() == null) {
            return false;
        }

        return true;
    }
}
