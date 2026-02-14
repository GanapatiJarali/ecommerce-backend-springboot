package org.ganapati.project.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ganapati.project.ecommerce.enums.AddressType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {

    private Long id;
    private String fullName;
    private String mobileNo;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
    private String pinCode;
    private AddressType addressType;
    private boolean isDefault;
}

