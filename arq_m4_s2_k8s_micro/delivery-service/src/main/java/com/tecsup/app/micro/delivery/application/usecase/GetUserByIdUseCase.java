package com.tecsup.app.micro.delivery.application.usecase;

import com.tecsup.app.micro.delivery.domain.exception.UserNotFoundException;
import com.tecsup.app.micro.delivery.domain.model.User;
import com.tecsup.app.micro.delivery.domain.repository.UserRepository;
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
    
    public User execute(Long id) {
        log.debug("Executing GetUserByIdUseCase for id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
