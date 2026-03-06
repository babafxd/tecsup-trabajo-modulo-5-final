package com.tecsup.app.micro.order.infrastructure.persistence.mapper;

import com.tecsup.app.micro.order.domain.model.Order;
import com.tecsup.app.micro.order.domain.model.OrderItem;
import com.tecsup.app.micro.order.infrastructure.persistence.entity.OrderEntity;
import com.tecsup.app.micro.order.infrastructure.persistence.entity.OrderItemEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderPersistenceMapper {

    Order toDomain(OrderEntity entity);
    OrderEntity toEntity(Order order);
    List<Order> toDomainList(List<OrderEntity> entities);

    OrderItem toDomain(OrderItemEntity entity);
    OrderItemEntity toEntity(OrderItem item);
}
