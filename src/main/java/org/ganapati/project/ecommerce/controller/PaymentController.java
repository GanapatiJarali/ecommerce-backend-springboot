package org.ganapati.project.ecommerce.controller;

import org.ganapati.project.ecommerce.dto.PaymentRequest;
import org.ganapati.project.ecommerce.dto.PaymentResponse;
import org.ganapati.project.ecommerce.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> makePayment(
            @RequestBody PaymentRequest request) {

        return ResponseEntity.ok(paymentService.doPayment(request));
    }
    //refund
    // paymentHistory
    //
}

