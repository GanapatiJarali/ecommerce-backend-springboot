package org.ganapati.project.ecommerce.dto;

import lombok.Data;

@Data
public class ProductResponse {
    private String productName;
    private Double price;
    private Integer stock;
    private boolean status;
    private Integer categoryId;
    private String productCode;
}
