package org.ganapati.project.ecommerce.dto;

import lombok.Data;
import org.ganapati.project.ecommerce.enums.AddressType;

@Data
public class AddressRequest {
    private String fullName;
    private String mobileNo;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
    private String pinCode;
    private AddressType addressType;
    private Boolean defaultAddress; //null //false
}
