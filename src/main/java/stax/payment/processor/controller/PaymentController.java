package stax.payment.processor.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import stax.payment.processor.service.PaymentFileService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentFileService paymentFileService;

    public PaymentController(PaymentFileService paymentFileService) {
        this.paymentFileService = paymentFileService;
    }


    @Operation(
            summary = "Process payment XML file",
            description = "Uploads a payment XML file and triggers the payment processing."
    )
    @PostMapping(
            value = "/files",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE
    )
    public ResponseEntity<Resource> processPaymentFile(
            @RequestPart("file") MultipartFile file) throws Exception {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Resource result = (Resource) paymentFileService.process(file.getInputStream());
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"processed-payments.xml\""
                )
                .contentType(MediaType.APPLICATION_XML)
                .body(result);
    }
}