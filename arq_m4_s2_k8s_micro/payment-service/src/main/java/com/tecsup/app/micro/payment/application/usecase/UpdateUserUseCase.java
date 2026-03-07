package com.tecsup.app.micro.payment.application.usecase;

import com.tecsup.app.micro.payment.domain.exception.DuplicateEmailException;
import com.tecsup.app.micro.payment.domain.exception.InvalidUserDataException;
import com.tecsup.app.micro.payment.domain.exception.UserNotFoundException;
import com.tecsup.app.micro.payment.domain.model.Payment;
import com.tecsup.app.micro.payment.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Caso de uso: Actualizar un usuario existente
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateUserUseCase {
    
    private final UserRepository userRepository;
    
    public Payment execute(Long id, Payment paymentDetails) {
        log.debug("Executing UpdateUserUseCase for id: {}", id);
        
        // Verificar que el usuario existe
        Payment existingPayment = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        
        // Validar datos del usuario
        if (!paymentDetails.isValid()) {
            throw new InvalidUserDataException("Invalid user data. Name and valid email are required.");
        }
        
        // Si el email cambió, verificar que no exista en otro usuario
        if (!existingPayment.getEmail().equals(paymentDetails.getEmail())) {
            if (userRepository.existsByEmail(paymentDetails.getEmail())) {
                throw new DuplicateEmailException(paymentDetails.getEmail());
            }
        }
        
        // Actualizar campos
        existingPayment.setName(paymentDetails.getName());
        existingPayment.setEmail(paymentDetails.getEmail());
        existingPayment.setPhone(paymentDetails.getPhone());
        existingPayment.setAddress(paymentDetails.getAddress());
        
        // Guardar cambios
        Payment updatedPayment = userRepository.save(existingPayment);
        log.info("User updated successfully with id: {}", updatedPayment.getId());
        
        return updatedPayment;
    }
}
