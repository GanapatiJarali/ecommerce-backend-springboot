package org.ganapati.project.ecommerce.controller;

import jakarta.validation.Valid;
import org.ganapati.project.ecommerce.common.BaseResponse;
import org.ganapati.project.ecommerce.dto.*;
import org.ganapati.project.ecommerce.entity.OrderItem;
import org.ganapati.project.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<OrderResponse>> placeOrder(@RequestBody @Valid OrderRequest orderRequest) {
        return new ResponseEntity<>(orderService.placeOrder(orderRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<PageResponse<OrderRes>>> getAllOrders(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "5") int size) {
        return ResponseEntity.ok((orderService.getOrdersByUser(page, size)));
    }

    @GetMapping("/{orderGroupId}")
    public ResponseEntity<BaseResponse<OrderResponse>> getOrderDetails(@PathVariable("orderGroupId") String orderGroupId) {
        return ResponseEntity.ok(orderService.getOrdersById(orderGroupId));
    }

    @GetMapping("/items/{orderItemId}")
    public ResponseEntity<BaseResponse<OrderItemResponse>> fetchOrderItemId(@PathVariable("orderItemId") String orderItemId) {
        return ResponseEntity.ok(orderService.fetchOrderItemId(orderItemId));
    }

    @GetMapping("/items")
    public ResponseEntity<BaseResponse<List<OrderItem>>> fetchOrderHistoryStateByUser(@RequestParam(name = "status") String status) {
        return ResponseEntity.ok(orderService.fetchOrderHistoryStatusByUser(status));
    }

    @PostMapping("/items/{orderItemId}/cancel")

    public ResponseEntity<BaseResponse<OrderItemResponse>> cancelOrder(@PathVariable("orderItemId") String orderItemId) {
        return ResponseEntity.ok(orderService.cancelOrder(orderItemId));
    }

    @PostMapping("/items/{orderItemId}/return")
    public ResponseEntity<BaseResponse<OrderReturnResponse>> orderReturn(@PathVariable("orderItemId") String orderItemId, @RequestBody OrderReturnRequest orderRequest) {
        return ResponseEntity.ok(orderService.orderReturn(orderItemId, orderRequest));
    }


}

