package org.ganapati.project.ecommerce.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import org.ganapati.project.ecommerce.entity.OrderItem;
import org.ganapati.project.ecommerce.enums.RefundStatus;

import java.math.BigDecimal;

@Data
public class RefundResponse {

    private Long id;

    private OrderItem orderItem;
    private RefundStatus refundStatus;
    private BigDecimal refundAmount;
    private String transactionId;
}
