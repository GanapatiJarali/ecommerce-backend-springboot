package org.ganapati.project.ecommerce.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.ganapati.project.ecommerce.common.ModelErrorConstant;
import org.ganapati.project.ecommerce.entity.OrderItem;
import org.ganapati.project.ecommerce.enums.RefundStatus;

import java.math.BigDecimal;

@Data
public class RefundRequest {
    @NotBlank(message = ModelErrorConstant.ORDER_ITEM_ID_MANDATORY)
    private String orderItemId;
}
