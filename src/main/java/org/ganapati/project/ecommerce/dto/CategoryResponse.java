package org.ganapati.project.ecommerce.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryResponse implements Serializable {
    private Long id;
    private String name;
    private boolean status;
}
