package org.ganapati.project.ecommerce.controller;


import jakarta.validation.Valid;
import org.ganapati.project.ecommerce.common.BaseResponse;
import org.ganapati.project.ecommerce.dto.OrderUpdateStatusRequest;
import org.ganapati.project.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/admin/orders")
public class OrderControllerV1 {
    @Autowired
    private OrderService orderService;

    //Update Order Status (ADMIN / DELIVERY AGENT)
    @PutMapping("/{orderId}/status")
    public ResponseEntity<BaseResponse> updateOrderStatus(@PathVariable("orderId") String orderId, @RequestBody @Valid OrderUpdateStatusRequest orderUpdateStatusRequest) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, orderUpdateStatusRequest));
    }


}
