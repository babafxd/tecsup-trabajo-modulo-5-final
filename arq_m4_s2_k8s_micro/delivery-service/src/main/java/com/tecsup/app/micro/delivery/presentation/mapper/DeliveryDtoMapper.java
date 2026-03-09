package com.tecsup.app.micro.delivery.presentation.mapper;

import com.tecsup.app.micro.delivery.domain.model.Delivery;
import com.tecsup.app.micro.delivery.presentation.dto.CreateDeliveryRequest;
import com.tecsup.app.micro.delivery.presentation.dto.DeliveryResponse;
import com.tecsup.app.micro.delivery.presentation.dto.UpdateDeliveryRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DeliveryDtoMapper {

    Delivery toDomain(CreateDeliveryRequest request);
    Delivery toDomain(UpdateDeliveryRequest request);
    DeliveryResponse toResponse(Delivery delivery);
    List<DeliveryResponse> toResponseList(List<Delivery> deliveries);
}
