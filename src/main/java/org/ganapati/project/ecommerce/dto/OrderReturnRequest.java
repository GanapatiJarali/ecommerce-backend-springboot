package org.ganapati.project.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.ganapati.project.ecommerce.common.ModelErrorConstant;

@Data
public class OrderReturnRequest {
    @NotBlank(message = ModelErrorConstant.REASON_MANDATORY)
    private String reason;
}
