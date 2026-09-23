package stax.payment.processor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import stax.payment.processor.service.PaymentFileService;

import java.io.InputStream;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentFileService paymentFileService;

    public PaymentController(PaymentFileService paymentFileService) {
        this.paymentFileService = paymentFileService;
    }

    @PostMapping("/files")
    public ResponseEntity<String> uploadPaymentFile(
            @RequestParam("file") MultipartFile file) throws Exception {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("File cannot be empty");
        }

        paymentFileService.process((InputStream) file);

        return ResponseEntity.ok("Payment file processed successfully");
    }
}
