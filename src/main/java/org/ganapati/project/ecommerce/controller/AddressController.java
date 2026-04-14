package org.ganapati.project.ecommerce.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.ganapati.project.ecommerce.common.BaseResponse;
import org.ganapati.project.ecommerce.dto.AddressRequest;
import org.ganapati.project.ecommerce.dto.AddressResponse;
import org.ganapati.project.ecommerce.dto.PageResponse;
import org.ganapati.project.ecommerce.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
@Validated
public class AddressController {

    private final AddressService addressService;

    // Add address for user
    @PostMapping
    public ResponseEntity<AddressResponse> addAddress(@RequestBody @Valid AddressRequest request
    ) {
        return new ResponseEntity<>(addressService.addAddress(request), HttpStatus.CREATED);
    }

    // Get all addresses of user
    @GetMapping
    public ResponseEntity<BaseResponse<PageResponse<AddressResponse>>> fetchAllAddressesByUser(@RequestParam(name = "page") @Min(value = 0, message = "Invalid page Number") int page, @RequestParam(name = "size") @Min(value = 1, message = "Page size at least 1") int size) {

        return ResponseEntity.ok(addressService.getAddressesByUser(page, size));
    }


    @GetMapping("/{addressId}")
    public ResponseEntity<BaseResponse<AddressResponse>> fetchAddressByAddressById(@PathVariable(name = "addressId") @Min(value = 1, message = "AddressId Starts With One") Long addressId) {
        return ResponseEntity.ok(addressService.getAddressByAddressById(addressId));
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<BaseResponse> updateAddress(@PathVariable("addressId") @Min(value = 1, message = "AddressId Starts With One") Long addressId, @RequestBody AddressRequest addressRequest) {
        return ResponseEntity.ok(addressService.updateAddress(addressRequest, addressId));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<BaseResponse> deleteAddressById(@PathVariable long addressId) {
        return ResponseEntity.ok(addressService.deleteAddressById(addressId));
    }
}

