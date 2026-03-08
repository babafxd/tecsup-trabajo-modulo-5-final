package com.tecsup.app.micro.payment.infrastructure.client.mapper;

import com.tecsup.app.micro.payment.domain.model.Order;
import com.tecsup.app.micro.payment.infrastructure.client.dto.OrderDto;
import com.tecsup.app.micro.payment.presentation.dto.OrderResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderDtoMapper {
    Order toDomain(OrderDto dto);

    OrderResponse toResponse(Order order);
}
