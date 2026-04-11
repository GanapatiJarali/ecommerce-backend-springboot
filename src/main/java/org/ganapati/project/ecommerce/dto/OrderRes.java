package org.ganapati.project.ecommerce.dto;

import lombok.Data;
import org.ganapati.project.ecommerce.enums.OrderItemStatus;
import org.ganapati.project.ecommerce.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderRes {
    private String orderGroupId;
    private BigDecimal totalAmount;
    private String paymentMode;
    private LocalDateTime orderDate;
    private OrderItemStatus orderItemStatus;
    private PaymentStatus paymentStatus;
}
