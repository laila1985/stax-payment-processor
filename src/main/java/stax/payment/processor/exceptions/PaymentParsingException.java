package stax.payment.processor.exceptions;

public class PaymentParsingException extends RuntimeException {

    public PaymentParsingException(String message) {
        super(message);
    }

    public PaymentParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentParsingException(Throwable cause) {
        super(cause);
    }
}
