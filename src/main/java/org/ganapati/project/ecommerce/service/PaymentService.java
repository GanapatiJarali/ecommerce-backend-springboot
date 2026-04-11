package org.ganapati.project.ecommerce.service;

import org.ganapati.project.ecommerce.common.BaseResponse;
import org.ganapati.project.ecommerce.dto.*;

public interface PaymentService {

    BaseResponse<PaymentResponse> doPayment(PaymentRequest request);

    BaseResponse<PageResponse<PaymentRes>> fetchPaymentHistory(String status, int page, int size);

    BaseResponse<PaymentRes> fetchPaymentById(Long paymentId);

    BaseResponse<RefundResponse> refund(RefundRequest refundRequest);
}
