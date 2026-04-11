package org.ganapati.project.ecommerce.dto;

import lombok.Data;
import org.ganapati.project.ecommerce.enums.PaymentStatus;

@Data
public class PaymentResponse {

    private String orderGroupId;
    private String transactionId;
    private PaymentStatus status;
    // getters & setters
}

