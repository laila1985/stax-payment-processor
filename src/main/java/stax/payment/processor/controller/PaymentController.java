package com.example.payment.controller;

import com.example.payment.service.PaymentFileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentFileService paymentFileService;

    public PaymentController(PaymentFileService paymentFileService) {
        this.paymentFileService = paymentFileService;
    }

    @PostMapping("/files")
    public ResponseEntity<String> uploadPaymentFile(
            @RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("File cannot be empty");
        }

        paymentFileService.processFile(file);

        return ResponseEntity.ok("Payment file processed successfully");
    }
}
