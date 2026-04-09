package org.ganapati.project.ecommerce.service;

import org.ganapati.project.ecommerce.common.BaseResponse;

import org.ganapati.project.ecommerce.dto.*;
import org.ganapati.project.ecommerce.entity.OrderItem;

import java.util.List;

public interface OrderService {
    BaseResponse<OrderResponse> placeOrder(OrderRequest orderRequest);

    BaseResponse<PageResponse<OrderRes>> getOrdersByUser(int page, int size);

    BaseResponse<OrderResponse> getOrdersById(String orderId);

    BaseResponse<OrderItemResponse> fetchOrderItemId(String orderItemId);

    BaseResponse<List<OrderItem>> fetchOrderHistoryStatusByUser(String status);
    BaseResponse<PageResponse<OrderItemResponse>> fetchOrderHistoryStatus(String status, int page, int size);

    BaseResponse<OrderItemResponse> cancelOrder(String orderId);

    BaseResponse updateOrderStatus(String orderId, OrderUpdateStatusRequest orderUpdateStatusRequest);

    BaseResponse<OrderReturnResponse> orderReturn(String orderId, OrderReturnRequest orderRequest);
}
