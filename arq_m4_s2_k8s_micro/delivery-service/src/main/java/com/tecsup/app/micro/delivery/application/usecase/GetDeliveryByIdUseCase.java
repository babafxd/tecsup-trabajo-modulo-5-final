package com.tecsup.app.micro.delivery.application.usecase;

import com.tecsup.app.micro.delivery.domain.exception.UserNotFoundException;
import com.tecsup.app.micro.delivery.domain.model.Delivery;
import com.tecsup.app.micro.delivery.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Caso de uso: Obtener usuario por ID
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class GetDeliveryByIdUseCase {
    
    private final DeliveryRepository deliveryRepository;
    
    public Delivery execute(Long id) {
        log.debug("Executing GetUserByIdUseCase for id: {}", id);
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
