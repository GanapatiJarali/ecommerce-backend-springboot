package org.ganapati.project.ecommerce.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.ganapati.project.ecommerce.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentRes {
    private Long id;
    private Long orderId;
    private String paymentMode; // CARD, UPI, NET_BANKING
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private String transactionId;
    private LocalDateTime createdAt;
}
