package com.tecsup.app.micro.delivery.application.service;

import com.tecsup.app.micro.delivery.application.usecase.*;
import com.tecsup.app.micro.delivery.domain.model.Delivery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio de Aplicación de Usuario
 * Orquesta los casos de uso y maneja las transacciones
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryApplicationService {


    private final GetDeliveryByIdUseCase getDeliveryByIdUseCase;
    private final CreateDeliveryUseCase createDeliveryUseCase;
    private final UpdateDeliveryUseCase updateDeliveryUseCase;


    @Transactional(readOnly = true)
    public Delivery getDeliveryById(Long id) {
        return getDeliveryByIdUseCase.execute(id);
    }

    @Transactional
    public Delivery createDelivery(Delivery delivery, String jwt) {
        return createDeliveryUseCase.execute(delivery, jwt);
    }

    @Transactional
    public Delivery updateUser(Long id, Delivery delivery) {
        return updateDeliveryUseCase.execute(id, delivery);
    }

}
