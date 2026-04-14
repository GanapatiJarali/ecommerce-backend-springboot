package org.ganapati.project.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.ganapati.project.ecommerce.common.ModelErrorConstant;
import org.ganapati.project.ecommerce.enums.AddressType;

@Data
public class AddressRequest {
    @NotBlank(message = ModelErrorConstant.MOBILE_NUMBER_MANDATORY)
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid Mobile Number")
    private String mobileNo;
    @NotBlank(message = ModelErrorConstant.ADDRESS1_MANDATORY)
    private String addressLine1;
    private String addressLine2;
    @NotBlank(message = ModelErrorConstant.CITY_MANDATORY)
    private String city;
    @NotBlank(message = ModelErrorConstant.STATE_MANDATORY)
    private String state;
    @NotBlank(message = ModelErrorConstant.COUNTRY_MANDATORY)
    private String country;
    @NotBlank(message = ModelErrorConstant.PIN_CODE_MANDATORY)
    @Pattern(regexp = "^[1-9][0-9]{5}$", message = "Invalid PinCode Number")
    private String pinCode;
    @Pattern(regexp = "SHIPPING|BILLING", message = "Only SHIPPING or BILLING allowed")
    private String addressType;
    private Boolean defaultAddress; //null //false
}
