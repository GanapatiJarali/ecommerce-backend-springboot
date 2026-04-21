package org.ganapati.project.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.ganapati.project.ecommerce.common.ModelErrorConstant;

import java.time.LocalDate;

@Data
public class AdminUserRequest {
    @NotBlank(message = ModelErrorConstant.MOBILE_NUMBER_MANDATORY)
    @Pattern(regexp = "^[0-9]{10}$", message = ModelErrorConstant.MOBILE_NUMBER_INVALID)
    private String mobileNo;
    @NotBlank(message = ModelErrorConstant.USER_NAME_MANDATORY)
    private String name;
    private String gender;
    @NotBlank(message = ModelErrorConstant.EMAIL_MANDATORY)
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = ModelErrorConstant.INVALID_EMAIL_MANDATORY)
    private String email;
    @NotBlank(message = ModelErrorConstant.ROLE_MANDATORY)
    @Pattern(regexp = "^(ADMIN|SUPER_ADMIN)$", message = ModelErrorConstant.ROLE_INVALID)
    private String role;
    @NotBlank(message = ModelErrorConstant.PASSWORD_MANDATORY)
    private String password;
    private LocalDate dateOfBirth;
}
