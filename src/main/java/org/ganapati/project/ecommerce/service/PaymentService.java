package org.ganapati.project.ecommerce.service;

import org.ganapati.project.ecommerce.dto.PaymentRequest;
import org.ganapati.project.ecommerce.dto.PaymentResponse;

public interface PaymentService {

    PaymentResponse doPayment(PaymentRequest request);
}
