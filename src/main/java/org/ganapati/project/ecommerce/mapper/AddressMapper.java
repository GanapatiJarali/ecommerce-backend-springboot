package org.ganapati.project.ecommerce.mapper;

import org.ganapati.project.ecommerce.dto.AddressResponse;
import org.ganapati.project.ecommerce.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressResponse entityToResponse(Address address);
}
