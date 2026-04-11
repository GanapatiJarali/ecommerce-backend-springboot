package org.ganapati.project.ecommerce.dto;

import lombok.Data;
import org.ganapati.project.ecommerce.enums.OrderItemStatus;

@Data
public class OrderReturnResponse {
    private String OrderId;
    private String reason;
    private OrderItemStatus orderItemStatus;
}
