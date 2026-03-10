package com.tecsup.app.micro.delivery.application.usecase;

import com.tecsup.app.micro.delivery.domain.exception.DeliveryNotFoundException;
import com.tecsup.app.micro.delivery.domain.exception.DuplicateEmailException;
import com.tecsup.app.micro.delivery.domain.exception.InvalidUserDataException;
import com.tecsup.app.micro.delivery.domain.exception.UserNotFoundException;
import com.tecsup.app.micro.delivery.domain.model.Delivery;
import com.tecsup.app.micro.delivery.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Caso de uso: Actualizar un usuario existente
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateDeliveryUseCase {
    
    private final DeliveryRepository deliveryRepository;
    
    public Delivery execute(Long id, Delivery deliveryDetails) {
        log.debug("Executing UpdateDeliveryUseCase for id: {}", id);

        Delivery existingDelivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new DeliveryNotFoundException("Delivery not found: " + id));

        if(existingDelivery.getStatus().equals(Delivery.DeliveryStatus.DELIVERED)){
            throw new DeliveryNotFoundException("Delivery with id:"+ id + ", is DELIVERED");
        }


        existingDelivery.setStatus(deliveryDetails.getStatus());
        existingDelivery.setDeliveredAt(LocalDateTime.now());
        
        // Guardar cambios
        Delivery updatedDelivery = deliveryRepository.save(existingDelivery);
        log.info("Delivery updated successfully with id: {}", updatedDelivery.getId());
        
        return updatedDelivery;
    }
}
