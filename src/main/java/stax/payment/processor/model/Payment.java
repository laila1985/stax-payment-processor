package stax.payment.processor.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String paymentId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(
                    name = "name",
                    column = @Column(name = "debtor_name")
            ),
            @AttributeOverride(
                    name = "accountNumber",
                    column = @Column(name = "debtor_account")
            ),
            @AttributeOverride(
                    name = "bank",
                    column = @Column(name = "debtor_bank")
            )
    })
    private Party debtor;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(
                    name = "name",
                    column = @Column(name = "creditor_name")
            ),
            @AttributeOverride(
                    name = "accountNumber",
                    column = @Column(name = "creditor_account")
            ),
            @AttributeOverride(
                    name = "bank",
                    column = @Column(name = "creditor_bank")
            )
    })
    private Party creditor;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String currency;

    public Payment() {
    }

    public Payment(
            String paymentId,
            Party debtor,
            Party creditor,
            BigDecimal amount,
            String currency) {

        this.paymentId = paymentId;
        this.debtor = debtor;
        this.creditor = creditor;
        this.amount = amount;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Party getDebtor() {
        return debtor;
    }

    public void setDebtor(Party debtor) {
        this.debtor = debtor;
    }

    public Party getCreditor() {
        return creditor;
    }

    public void setCreditor(Party creditor) {
        this.creditor = creditor;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}