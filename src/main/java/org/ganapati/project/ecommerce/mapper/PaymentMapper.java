package org.ganapati.project.ecommerce.mapper;

import org.ganapati.project.ecommerce.dto.PaymentRes;
import org.ganapati.project.ecommerce.dto.PaymentResponse;
import org.ganapati.project.ecommerce.entity.Payment;
import org.mapstruct.Mapper;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentResponse paymentToPaymentResponse(Payment payment);

    PaymentRes paymentToPaymentRes(Payment payment);
    List<PaymentRes> paymentToPaymentResList(List<Payment> payment);
}
