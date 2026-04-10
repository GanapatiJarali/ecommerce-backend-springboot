package org.ganapati.project.ecommerce.service;

import org.ganapati.project.ecommerce.common.BaseResponse;
import org.ganapati.project.ecommerce.dto.PageResponse;
import org.ganapati.project.ecommerce.dto.PaymentRequest;
import org.ganapati.project.ecommerce.dto.PaymentRes;
import org.ganapati.project.ecommerce.dto.PaymentResponse;

public interface PaymentService {

    BaseResponse<PaymentResponse> doPayment(PaymentRequest request);

    BaseResponse<PageResponse<PaymentRes>> fetchPaymentHistory(String status, int page, int size);

    BaseResponse<PaymentRes> fetchPaymentById(Long paymentId);
}
