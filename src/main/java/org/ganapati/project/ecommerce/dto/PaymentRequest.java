package org.ganapati.project.ecommerce.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {

    private String orderGroupId;
    private BigDecimal amount;
    private String paymentMode;

    // getters & setters
}
