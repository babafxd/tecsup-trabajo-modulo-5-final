package com.tecsup.app.micro.delivery.infrastructure.client.mapper;

import com.tecsup.app.micro.delivery.domain.model.Order;
import com.tecsup.app.micro.delivery.infrastructure.client.dto.OrderDto;
import com.tecsup.app.micro.delivery.presentation.dto.OrderResponse;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderDtoMapper {
    Order toDomain(OrderDto dto);

    OrderResponse toResponse(Order order);
}
