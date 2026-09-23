package stax.payment.processor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stax.payment.processor.model.Payment;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByPaymentId(String paymentId);
}
