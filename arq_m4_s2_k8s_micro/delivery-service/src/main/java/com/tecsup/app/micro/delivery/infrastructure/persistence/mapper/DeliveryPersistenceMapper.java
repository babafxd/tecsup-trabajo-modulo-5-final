package com.tecsup.app.micro.delivery.infrastructure.persistence.mapper;

import com.tecsup.app.micro.delivery.domain.model.Delivery;
import com.tecsup.app.micro.delivery.infrastructure.persistence.entity.DeliveryEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DeliveryPersistenceMapper {

    Delivery toDomain(DeliveryEntity entity);

    DeliveryEntity toEntity(Delivery delivery);

    List<Delivery> toDomainList(List<DeliveryEntity> entities);

}
