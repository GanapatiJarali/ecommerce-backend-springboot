package org.ganapati.project.ecommerce.dto;

import lombok.Data;
import org.ganapati.project.ecommerce.enums.OrderItemStatus;
import org.ganapati.project.ecommerce.enums.PaymentStatus;

@Data
public class OrderUpdateResponse {
    private String orderId;
    private OrderItemStatus orderItemStatus;
    private PaymentStatus paymentStatus;
}
