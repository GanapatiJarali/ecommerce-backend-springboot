package org.ganapati.project.ecommerce.mapper;

import org.ganapati.project.ecommerce.dto.RefundResponse;
import org.ganapati.project.ecommerce.entity.Refund;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RefundMapper {
    RefundResponse refundToResponse(Refund refund);
}
