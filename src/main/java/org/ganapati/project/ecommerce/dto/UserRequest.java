package org.ganapati.project.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.ganapati.project.ecommerce.common.ModelErrorConstant;

import java.time.LocalDate;

@Data
public class UserRequest {
    @NotBlank(message = ModelErrorConstant.MOBILE_NUMBER_MANDATORY)
    @Pattern(regexp = "^[0-9]{10}$", message = ModelErrorConstant.MOBILE_NUMBER_INVALID)
    private String mobileNo;
    @NotBlank(message = ModelErrorConstant.USER_NAME_MANDATORY)
    private String name;
    @NotBlank(message = ModelErrorConstant.EMAIL_MANDATORY)
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = ModelErrorConstant.INVALID_EMAIL_MANDATORY)
    private String email; //mainIdentity
    @NotBlank(message = ModelErrorConstant.PASSWORD_MANDATORY)
    private String password;
    @NotBlank(message = ModelErrorConstant.GENDER_MANDATORY)
    @Pattern(regexp = "^(MALE|FEMALE)$", message = ModelErrorConstant.GENDER_INVALID)
    private String gender;
    @NotBlank(message = ModelErrorConstant.DATE_OF_BIRTH_MANDATORY)
    private LocalDate dateOfBirth;
}
