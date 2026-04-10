package org.ganapati.project.ecommerce.service;

//import org.example.ecommerce.client.OrderServiceClient;

import lombok.extern.slf4j.Slf4j;
import org.ganapati.project.ecommerce.common.BaseResponse;
import org.ganapati.project.ecommerce.config.JwtRequestContext;
import org.ganapati.project.ecommerce.dto.PageResponse;
import org.ganapati.project.ecommerce.dto.PaymentRequest;
import org.ganapati.project.ecommerce.dto.PaymentRes;
import org.ganapati.project.ecommerce.dto.PaymentResponse;
import org.ganapati.project.ecommerce.entity.Order;
import org.ganapati.project.ecommerce.entity.Payment;
import org.ganapati.project.ecommerce.entity.User;
import org.ganapati.project.ecommerce.enums.PaymentStatus;
import org.ganapati.project.ecommerce.exception.ValidationException;
import org.ganapati.project.ecommerce.mapper.PaymentMapper;
import org.ganapati.project.ecommerce.repository.OrderRepository;
import org.ganapati.project.ecommerce.repository.PaymentRepository;
import org.ganapati.project.ecommerce.util.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {


    private final PaymentRepository paymentRepository;

    private final OrderRepository orderRepository;


    private final CommonService commonService;
    private final PaymentMapper paymentMapper;
    private final JwtRequestContext jwtRequestContext;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderRepository orderRepository, CommonService commonService, PaymentMapper paymentMapper, JwtRequestContext jwtRequestContext) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.commonService = commonService;
        this.paymentMapper = paymentMapper;
        this.jwtRequestContext = jwtRequestContext;
    }

    @Override
    public BaseResponse<PaymentResponse> doPayment(PaymentRequest request) {
        log.info("paymentService PaymentRequest: {}", request);
        Order order = commonService.fetchOrderByOrderGroupId(request.getOrderGroupId()).orElseThrow(() -> new ValidationException(5001, "Order Group Id not Found", "Order Group Id not Found"));
        if (order.getPaymentStatus().equals(PaymentStatus.SUCCESS)) {
            throw new ValidationException(6001, "Duplicate payment.", "Duplicate payment.");
        }
        log.info("order dao: {} ", order);
        if (!order.getTotalAmount().equals(request.getAmount())) {
            throw new ValidationException(7001, "order amount mismatch ", "order amount mismatch");
        }
        boolean isSuccess = Math.random() > 0.2;
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentMode(request.getPaymentMode());
        payment.setAmount(request.getAmount());
        payment.setStatus(isSuccess ? PaymentStatus.SUCCESS : PaymentStatus.FAILED);
        payment.setTransactionId("TXN_" + UUID.randomUUID().toString());
        payment.setCreatedAt(LocalDateTime.now());
        paymentRepository.save(payment);
        log.info("payment info: {}", payment);
        if (payment.getStatus().equals(PaymentStatus.SUCCESS)) {
            order.setPaymentStatus(PaymentStatus.SUCCESS);
        } else {
            order.setPaymentStatus(PaymentStatus.FAILED);
        }
        orderRepository.save(order);
        PaymentResponse paymentResponse = paymentMapper.paymentToPaymentResponse(payment);
        return BaseResponse.success(paymentResponse);
    }

    @Override
    public BaseResponse<PageResponse<PaymentRes>> fetchPaymentHistory(String status, int page, int size) {
        String email = jwtRequestContext.getEmail();
        User user = commonService.findByEmail(email);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Payment> paymentPage = paymentRepository.findPaymentHistoryByUserId(user.getId(), pageable);
        PageResponse<PaymentRes> pageResponse = new PageResponse<>();
        pageResponse.setData(paymentMapper.paymentToPaymentResList(paymentPage.getContent()));
        pageResponse.setTotalPages(paymentPage.getTotalPages());
        pageResponse.setTotalElements(paymentPage.getTotalElements());
        return BaseResponse.success(pageResponse);
    }

    @Override
    public BaseResponse<PaymentRes> fetchPaymentById(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new ValidationException(9001, "Payment not found.", " Payment not found."));
        PaymentRes paymentRes = paymentMapper.paymentToPaymentRes(payment);
        return BaseResponse.success(paymentRes);
    }

}
