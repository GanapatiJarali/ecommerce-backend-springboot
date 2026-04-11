package org.ganapati.project.ecommerce.mapper;

import org.ganapati.project.ecommerce.dto.OrderRes;
import org.ganapati.project.ecommerce.dto.OrderResponse;
import org.ganapati.project.ecommerce.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "createdAt", target = "orderDate")
    OrderRes entityToOrderRes(Order order);

    @Mapping(source = "createdAt", target = "orderDate")
    OrderResponse entityToOrderResponse(Order order);
}
