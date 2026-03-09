package com.tecsup.app.micro.delivery.domain.repository;

import com.tecsup.app.micro.delivery.domain.model.Delivery;
import java.util.Optional;


public interface DeliveryRepository {
    Optional<Delivery> findById(Long id);
    Delivery save(Delivery delivery);
}
