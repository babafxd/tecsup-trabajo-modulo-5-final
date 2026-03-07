package com.tecsup.app.micro.payment.application.usecase;

import com.tecsup.app.micro.payment.domain.exception.DuplicateEmailException;
import com.tecsup.app.micro.payment.domain.exception.InvalidUserDataException;
import com.tecsup.app.micro.payment.domain.model.Payment;
import com.tecsup.app.micro.payment.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Caso de uso: Crear un nuevo usuario
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CreateUserUseCase {
    
    private final UserRepository userRepository;
    
    public Payment execute(Payment payment) {
        log.debug("Executing CreateUserUseCase for email: {}", payment.getEmail());
        
        // Validar datos del usuario
        if (!payment.isValid()) {
            throw new InvalidUserDataException("Invalid user data. Name and valid email are required.");
        }
        
        // Verificar que el email no exista
        if (userRepository.existsByEmail(payment.getEmail())) {
            throw new DuplicateEmailException(payment.getEmail());
        }
        
        // Guardar usuario
        Payment savedPayment = userRepository.save(payment);
        log.info("User created successfully with id: {}", savedPayment.getId());
        
        return savedPayment;
    }
}
