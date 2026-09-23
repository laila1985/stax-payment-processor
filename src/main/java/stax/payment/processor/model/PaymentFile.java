package stax.payment.processor.model;

import java.time.LocalDate;

public class PaymentFile {

    private String fileId;

    private LocalDate creationDate;

    public PaymentFile() {
    }

    public PaymentFile(String fileId, LocalDate creationDate) {
        this.fileId = fileId;
        this.creationDate = creationDate;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
