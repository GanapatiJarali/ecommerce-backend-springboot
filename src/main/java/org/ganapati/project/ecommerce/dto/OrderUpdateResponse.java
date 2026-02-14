package org.ganapati.project.ecommerce.dto;

import lombok.Data;
import org.ganapati.project.ecommerce.enums.OrderStatus;
import org.ganapati.project.ecommerce.enums.PaymentStatus;

@Data
public class OrderUpdateResponse {
    private String orderId;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
}
