package com.tecsup.app.micro.payment.presentation.mapper;
import com.tecsup.app.micro.payment.domain.model.Payment;
import com.tecsup.app.micro.payment.presentation.dto.CreatePaymentRequest;
import com.tecsup.app.micro.payment.presentation.dto.PaymentResponse;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface PaymentDtoMapper {

    Payment toDomain(CreatePaymentRequest request);
    PaymentResponse toResponse(Payment payment);
    List<PaymentResponse> toResponseList(List<Payment> payments);
}
