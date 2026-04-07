package org.ganapati.project.ecommerce.exception;

import lombok.Data;

@Data
public class ErrorResponse {
    private int errorCode;
    private String errorDescription;
}
