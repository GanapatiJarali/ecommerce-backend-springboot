package org.ganapati.project.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.ganapati.project.ecommerce.common.ModelErrorConstant;

@Data
public class ProductRequest {
    @NotBlank(message = ModelErrorConstant.PRODUCT_NAME_MANDATORY)
    private String name;
    @NotBlank(message = ModelErrorConstant.PRODUCT_PRICE_MANDATORY)
    private Double price;
    @NotBlank(message = ModelErrorConstant.PRODUCT_STOCK_MANDATORY)
    private Integer stock;
    @NotBlank(message = ModelErrorConstant.PRODUCT_CATEGORY_MANDATORY)
    private Long category; //optional
    private Boolean status;
    @NotBlank(message = ModelErrorConstant.PRODUCT_CODE_MANDATORY)
    private String productCode;
}
