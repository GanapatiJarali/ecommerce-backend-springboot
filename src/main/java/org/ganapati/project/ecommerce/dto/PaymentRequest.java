package org.ganapati.project.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.ganapati.project.ecommerce.common.ModelErrorConstant;

import java.math.BigDecimal;

@Data
public class PaymentRequest {
    @NotBlank(message = ModelErrorConstant.ORDER_GROUP_ID_MANDATORY)
    private String orderGroupId;
    @NotBlank(message = ModelErrorConstant.AMOUNT_MANDATORY)
    private BigDecimal amount;
    @NotBlank(message = ModelErrorConstant.PAYMENT_MODE_MANDATORY)
    private String paymentMode;
    // getters & setters
}
