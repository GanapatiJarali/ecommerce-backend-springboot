package org.ganapati.project.ecommerce.service;

//import org.example.ecommerce.client.OrderServiceClient;
import org.ganapati.project.ecommerce.dto.PaymentRequest;
import org.ganapati.project.ecommerce.dto.PaymentResponse;
import org.ganapati.project.ecommerce.entity.Payment;
import org.ganapati.project.ecommerce.enums.PaymentStatus;
import org.ganapati.project.ecommerce.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

//    @Autowired
//    private OrderServiceClient orderServiceClient;

    @Override
    public PaymentResponse doPayment(PaymentRequest request) {

        Payment payment = new Payment();
        payment.setOrderId(request.getOrderId());
        payment.setAmount(request.getAmount());
        payment.setPaymentMode(request.getPaymentMode());
        payment.setCreatedAt(LocalDateTime.now());

        // MOCK PAYMENT GATEWAY LOGIC
        boolean paymentSuccess = request.getAmount() <= 50000;

        if (paymentSuccess) {
            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setTransactionId(UUID.randomUUID().toString());
        } else {
            payment.setStatus(PaymentStatus.FAILED);
            payment.setTransactionId("TXN_FAILED");
        }
        paymentRepository.save(payment);
        // Notify Order Service
//        orderServiceClient.updatePaymentStatus(
//                request.getOrderId(),
//                payment.getStatus().name()
//        );
        PaymentResponse response = new PaymentResponse();
        response.setOrderId(request.getOrderId());
        response.setTransactionId(payment.getTransactionId());
        response.setStatus(payment.getStatus());
        response.setMessage("Payment " + payment.getStatus());
        return response;
    }
}
