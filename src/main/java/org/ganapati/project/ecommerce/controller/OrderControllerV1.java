package org.ganapati.project.ecommerce.controller;

import jakarta.validation.Valid;
import org.ganapati.project.ecommerce.common.BaseResponse;
import org.ganapati.project.ecommerce.dto.OrderItemResponse;
import org.ganapati.project.ecommerce.dto.OrderUpdateStatusRequest;
import org.ganapati.project.ecommerce.dto.PageResponse;
import org.ganapati.project.ecommerce.entity.OrderItem;
import org.ganapati.project.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/admin")
public class OrderControllerV1 {

    private final OrderService orderService;

    @Autowired
    public OrderControllerV1(OrderService orderService) {
        this.orderService = orderService;
    }

    //Update Order Status (ADMIN / DELIVERY AGENT)
    @PutMapping("/items/{orderItemId}/status")
    public ResponseEntity<BaseResponse> updateOrderStatus(@PathVariable("orderItemId") String orderItemId, @RequestBody @Valid OrderUpdateStatusRequest orderUpdateStatusRequest) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderItemId, orderUpdateStatusRequest));
    }

    @GetMapping("/items/status")
    public ResponseEntity<BaseResponse<PageResponse<OrderItemResponse>>> fetchOrderHistoryStatus(@RequestParam(name = "status") String status, @RequestParam(name = "page", required = false, defaultValue = "0") int page, @RequestParam(name = "size", required = false, defaultValue = "5") int size) {
        return ResponseEntity.ok(orderService.fetchOrderHistoryStatus(status, page, size));
    }
}
