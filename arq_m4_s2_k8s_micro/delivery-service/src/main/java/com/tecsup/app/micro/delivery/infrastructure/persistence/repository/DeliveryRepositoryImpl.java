package com.tecsup.app.micro.delivery.infrastructure.persistence.repository;

import com.tecsup.app.micro.delivery.domain.model.Delivery;
import com.tecsup.app.micro.delivery.domain.repository.DeliveryRepository;
import com.tecsup.app.micro.delivery.infrastructure.persistence.entity.DeliveryEntity;
import com.tecsup.app.micro.delivery.infrastructure.persistence.mapper.DeliveryPersistenceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
@RequiredArgsConstructor
@Slf4j
public class DeliveryRepositoryImpl implements DeliveryRepository {

    private final JpaDeliveryRepository jpaDeliveryRepository;
    private final DeliveryPersistenceMapper mapper;

    @Override
    public Optional<Delivery> findById(Long id) {
        log.debug("Finding user by id: {}", id);
        return jpaDeliveryRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Delivery save(Delivery delivery) {
        log.debug("Saving Delivery for orderId: {}", delivery.getOrderId());
        DeliveryEntity entity = mapper.toEntity(delivery);
        DeliveryEntity savedEntity = jpaDeliveryRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public List<Delivery> findAll() {
        log.debug("Finding all deliveries");
        return jpaDeliveryRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

}
