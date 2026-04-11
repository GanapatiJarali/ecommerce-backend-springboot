package org.ganapati.project.ecommerce.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.ganapati.project.ecommerce.entity.OrderItem;
import org.ganapati.project.ecommerce.enums.RefundStatus;

import java.math.BigDecimal;

@Data
public class RefundRequest {
    private String orderItemId;
}
