package org.ganapati.project.ecommerce.service;

import org.ganapati.project.ecommerce.common.BaseResponse;
import org.ganapati.project.ecommerce.dto.AddressRequest;
import org.ganapati.project.ecommerce.dto.AddressResponse;
import org.ganapati.project.ecommerce.dto.PageResponse;

public interface AddressService {
    AddressResponse addAddress(AddressRequest request);

    BaseResponse<PageResponse<AddressResponse>> getAddressesByUser(int page, int size);

    BaseResponse<AddressResponse> getAddressByAddressById(Long addressId);

    BaseResponse<Void> updateAddress(AddressRequest addressRequest, Long addressId);

    BaseResponse deleteAddressById(Long addressId);
}
