package com.tecsup.app.micro.order.infrastructure.persistence.repository;

import com.tecsup.app.micro.order.domain.model.Order;
import com.tecsup.app.micro.order.domain.repository.OrderRepository;
import com.tecsup.app.micro.order.infrastructure.persistence.entity.OrderEntity;
import com.tecsup.app.micro.order.infrastructure.persistence.mapper.OrderPersistenceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OrderRepositoryImpl implements OrderRepository {

    private final JpaOrderRepository jpaOrderRepository;
    private final OrderPersistenceMapper mapper;

    @Override
    public Order save(Order order) {
        log.debug("Saving Order user id: {}", order.getUserId());
        OrderEntity entity = mapper.toEntity(order);

        if (entity.getItems() != null) {
            entity.getItems().forEach(item -> item.setOrder(entity));
        }

        OrderEntity savedEntity = jpaOrderRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }


}
