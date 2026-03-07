package com.tecsup.app.micro.payment.application.usecase;

import com.tecsup.app.micro.payment.domain.exception.UserNotFoundException;
import com.tecsup.app.micro.payment.domain.model.Payment;
import com.tecsup.app.micro.payment.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Caso de uso: Obtener usuario por ID
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class GetUserByIdUseCase {
    
    private final UserRepository userRepository;
    
    public Payment execute(Long id) {
        log.debug("Executing GetUserByIdUseCase for id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
