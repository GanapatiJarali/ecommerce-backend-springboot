package org.ganapati.project.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.ganapati.project.ecommerce.common.BaseResponse;
import org.ganapati.project.ecommerce.dto.AddressRequest;
import org.ganapati.project.ecommerce.dto.AddressResponse;
import org.ganapati.project.ecommerce.dto.PageResponse;
import org.ganapati.project.ecommerce.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    // Add address for user
    @PostMapping
    public ResponseEntity<AddressResponse> addAddress(
            @RequestBody AddressRequest request
    ) {
        return new ResponseEntity<>(addressService.addAddress(request), HttpStatus.CREATED);
    }

    // Get all addresses of user
    @GetMapping
    public ResponseEntity<BaseResponse<PageResponse<AddressResponse>>> fetchAllAddressesByUser(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {

        return ResponseEntity.ok(addressService.getAddressesByUser(page, size));
    }
    //TODO new api for the getAddressByAddressById.

    @GetMapping("/{addressId}")
    public ResponseEntity<BaseResponse<AddressResponse>> fetchAddressByAddressById(@PathVariable(name = "addressId") Long addressId) {
        return ResponseEntity.ok(addressService.getAddressByAddressById(addressId));
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<BaseResponse> updateAddress(@PathVariable("addressId") Long addressId, @RequestBody AddressRequest addressRequest) {
        return ResponseEntity.ok(addressService.updateAddress(addressRequest, addressId));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<BaseResponse> deleteAddressById(@PathVariable long addressId) {
        return ResponseEntity.ok(addressService.deleteAddressById(addressId));
    }
}

