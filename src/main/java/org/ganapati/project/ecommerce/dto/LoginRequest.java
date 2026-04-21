package org.ganapati.project.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.ganapati.project.ecommerce.common.ModelErrorConstant;

@Data
public class LoginRequest {
    @NotBlank(message = ModelErrorConstant.EMAIL_MANDATORY)
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = ModelErrorConstant.INVALID_EMAIL_MANDATORY)
    private String email;
    @NotBlank(message = ModelErrorConstant.INVALID_EMAIL_MANDATORY)
    private String password;
}

