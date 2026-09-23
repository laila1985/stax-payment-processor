package com.example.payment.validator;

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

        if (payment.getDebtor() == null) {
            return false;
        }

        if (payment.getCreditor() == null) {
            return false;
        }

        return true;
    }
}
