package org.ganapati.project.ecommerce.dto;

import lombok.Data;

@Data
public class CartRequest {
    private Long product;

    private Integer quantity;
}
