package com.tecsup.app.micro.delivery.infrastructure.persistence.repository;

import com.tecsup.app.micro.delivery.infrastructure.persistence.entity.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDeliveryRepository extends JpaRepository<DeliveryEntity, Long> {

}
