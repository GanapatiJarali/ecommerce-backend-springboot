package org.ganapati.project.ecommerce.service;

import org.ganapati.project.ecommerce.common.BaseResponse;

import org.ganapati.project.ecommerce.dto.*;

public interface OrderService {
    BaseResponse<OrderResponse> placeOrder(OrderRequest orderRequest);

    BaseResponse<PageResponse<OrderRes>> getOrdersByUserId(int page, int size);

    BaseResponse<OrderResponse> getOrdersById(String orderId);

    BaseResponse<OrderResponse> cancelOrder(String orderId);

    BaseResponse updateOrderStatus(String orderId, OrderUpdateStatusRequest orderUpdateStatusRequest);

    BaseResponse<OrderReturnResponse> orderReturn(String orderId, OrderReturnRequest orderRequest);
}
