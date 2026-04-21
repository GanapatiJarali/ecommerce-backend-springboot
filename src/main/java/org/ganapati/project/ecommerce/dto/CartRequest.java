package org.ganapati.project.ecommerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.ganapati.project.ecommerce.common.ModelErrorConstant;

@Data
public class CartRequest {
    @NotBlank(message = ModelErrorConstant.PRODUCT_ID_MANDATORY)
    @Min(value = 1, message = ModelErrorConstant.INVALID_PRODUCT_ID)
    private Long product;
    @NotBlank(message = ModelErrorConstant.QUANTITY_MANDATORY)
    @Min(value = 1, message = ModelErrorConstant.INVALID_QUANTITY_ID)
    private Integer quantity;
}
