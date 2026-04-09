package org.ganapati.project.ecommerce.dto;


import lombok.*;
import org.ganapati.project.ecommerce.enums.OrderItemStatus;
import org.ganapati.project.ecommerce.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class OrderResponse {

    private String orderGroupId;
    private BigDecimal totalAmount;
    private PaymentStatus paymentStatus;
    private LocalDateTime orderDate;
    private String paymentMode;
    private List<OrderItemResponse> items;
}
