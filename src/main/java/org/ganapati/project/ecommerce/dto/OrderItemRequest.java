package org.ganapati.project.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.ganapati.project.ecommerce.common.ModelErrorConstant;

@Getter
@Setter
public class OrderItemRequest {
    @NotBlank(message = ModelErrorConstant.PRODUCT_ID_MANDATORY)
    private Long productId;
    @NotBlank(message = ModelErrorConstant.INVALID_QUANTITY_ID)
    private Integer quantity;
}
