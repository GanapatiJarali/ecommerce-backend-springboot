package org.ganapati.project.ecommerce.mapper;

import org.ganapati.project.ecommerce.dto.CartResponse;
import org.ganapati.project.ecommerce.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "cartId", source = "id")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "quantity", source = "quantity")
    CartResponse entityToCartResponse(Cart cart);
}
