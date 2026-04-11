package org.ganapati.project.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.ganapati.project.ecommerce.enums.RefundStatus;

import java.math.BigDecimal;

@Data
@Entity
public class Refund extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name ="orderItemId" )
    private OrderItem orderItem;
    private RefundStatus refundStatus;
    private BigDecimal refundAmount;
    private String transactionId;
}
