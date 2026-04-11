package org.ganapati.project.ecommerce.service;

//import org.example.ecommerce.client.OrderServiceClient;

import lombok.extern.slf4j.Slf4j;
import org.ganapati.project.ecommerce.common.BaseResponse;
import org.ganapati.project.ecommerce.config.JwtRequestContext;
import org.ganapati.project.ecommerce.dto.*;
import org.ganapati.project.ecommerce.entity.*;
import org.ganapati.project.ecommerce.enums.OrderItemStatus;
import org.ganapati.project.ecommerce.enums.PaymentStatus;
import org.ganapati.project.ecommerce.enums.RefundStatus;
import org.ganapati.project.ecommerce.exception.ValidationException;
import org.ganapati.project.ecommerce.mapper.PaymentMapper;
import org.ganapati.project.ecommerce.mapper.RefundMapper;
import org.ganapati.project.ecommerce.repository.OrderRepository;
import org.ganapati.project.ecommerce.repository.PaymentRepository;
import org.ganapati.project.ecommerce.repository.RefundRepository;
import org.ganapati.project.ecommerce.util.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Ref;
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
    private final RefundMapper refundMapper;
    private final RefundRepository refundRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderRepository orderRepository, CommonService commonService, PaymentMapper paymentMapper, JwtRequestContext jwtRequestContext, RefundMapper refundMapper, RefundRepository refundRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.commonService = commonService;
        this.paymentMapper = paymentMapper;
        this.jwtRequestContext = jwtRequestContext;
        this.refundMapper = refundMapper;
        this.refundRepository = refundRepository;
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
        paymentResponse.setOrderGroupId(request.getOrderGroupId());
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

    @Override
    public BaseResponse<RefundResponse> refund(RefundRequest refundRequest) {
        log.info("refund refundRequest : {} ", refundRequest);
        OrderItem orderItem = commonService.fetchByOrderItemId(refundRequest.getOrderItemId());
        Optional<Refund> refundOptional = refundRepository.findByOrderItem_Id(orderItem.getId());
        if (refundOptional.isPresent()) {
            if (refundOptional.get().getRefundStatus().equals(RefundStatus.SUCCESS)) {
                throw new ValidationException(11000, "Already refunded the amount", "Already refunded the amount");
            }
        }
        boolean isSuccess = Math.random() > 0.2;
        Refund refund = new Refund();
        refund.setOrderItem(orderItem);
        refund.setRefundAmount(orderItem.getPrice());
        refund.setTransactionId(UUID.randomUUID().toString());
        refund.setRefundStatus(isSuccess ? RefundStatus.SUCCESS : RefundStatus.FAILED);
        refundRepository.save(refund);
        RefundResponse refundResponse = refundMapper.refundToResponse(refund);
        log.info("refund Response: {}", refundResponse);
        return BaseResponse.success(refundResponse);
    }

}
