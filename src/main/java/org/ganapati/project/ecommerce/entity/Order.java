package org.ganapati.project.ecommerce.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ganapati.project.ecommerce.enums.PaymentStatus;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Who placed the order
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(unique = true)
    private String orderGroupId;

    // Shipping address snapshot
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;

    // Total amount calculated on server
    private BigDecimal totalAmount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private String paymentMode; // COD, UPI, CARD


}
