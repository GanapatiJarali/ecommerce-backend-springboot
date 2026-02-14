package org.ganapati.project.ecommerce.mapper;

import org.ganapati.project.ecommerce.dto.OrderItemResponse;
import org.ganapati.project.ecommerce.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(source ="product.name", target ="productName")
    @Mapping(source ="product.id", target ="productId")
    OrderItemResponse entityToResponse(OrderItem orderItem);
}
