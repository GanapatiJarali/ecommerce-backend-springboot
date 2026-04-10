package org.ganapati.project.ecommerce.controller;

import org.ganapati.project.ecommerce.common.BaseResponse;
import org.ganapati.project.ecommerce.dto.PageResponse;
import org.ganapati.project.ecommerce.dto.PaymentRequest;
import org.ganapati.project.ecommerce.dto.PaymentRes;
import org.ganapati.project.ecommerce.dto.PaymentResponse;
import org.ganapati.project.ecommerce.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<PaymentResponse>> makePayment(@RequestBody PaymentRequest request) {
        return new ResponseEntity<>(paymentService.doPayment(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<PageResponse<PaymentRes>>> fetchPaymentHistory(@RequestParam String status, int page, int size) {
        return ResponseEntity.ok(paymentService.fetchPaymentHistory(status, page, size));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<BaseResponse<PaymentRes>> fetchPaymentById(@PathVariable("{paymentId}") Long paymentId) {
        return ResponseEntity.ok(paymentService.fetchPaymentById(paymentId));
    }
    //refund
    // paymentHistory
    //
}

