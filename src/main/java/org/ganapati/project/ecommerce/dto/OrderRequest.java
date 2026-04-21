package org.ganapati.project.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.ganapati.project.ecommerce.common.ModelErrorConstant;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
@NotBlank(message = ModelErrorConstant.ADDRESS_ID_MANDATORY)
    private Long addressId;

    private List<OrderItemRequest> items;
}
