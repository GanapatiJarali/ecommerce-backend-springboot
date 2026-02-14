package org.ganapati.project.ecommerce.controller;

import jakarta.validation.Valid;
import org.ganapati.project.ecommerce.common.BaseResponse;

import org.ganapati.project.ecommerce.service.OrderServiceImpl;
import org.ganapati.project.ecommerce.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/orders")
public class OrderController {
    @Autowired
    private OrderServiceImpl orderService;

    @PostMapping
    public ResponseEntity<BaseResponse<OrderResponse>> placeOrder(@RequestBody @Valid OrderRequest orderRequest) {
        return new ResponseEntity<>(orderService.placeOrder(orderRequest), HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<BaseResponse<PageResponse<OrderRes>>> getOrdersByUser(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "5") int size) {
        return ResponseEntity.ok((orderService.getOrdersByUserId(page, size)));
    }

    @GetMapping("/user/{orderId}")
    public ResponseEntity<BaseResponse<OrderResponse>> fetchOrderById(@PathVariable("orderId") String orderId) {
        return ResponseEntity.ok(orderService.getOrdersById(orderId));
    }

    @DeleteMapping("/user/{orderId}")
    public ResponseEntity<BaseResponse<OrderResponse>> cancelOrder(@PathVariable("orderId") String orderId) {
        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }

    @PostMapping("/{orderId}/return")
    public ResponseEntity<BaseResponse<OrderReturnResponse>> orderReturn(@PathVariable("orderId") String orderId, @RequestBody OrderReturnRequest orderRequest) {
        return ResponseEntity.ok(orderService.orderReturn(orderId, orderRequest));
    }
}

