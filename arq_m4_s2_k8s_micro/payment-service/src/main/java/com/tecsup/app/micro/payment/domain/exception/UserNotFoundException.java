package com.tecsup.app.micro.payment.domain.exception;

/**
 * Excepción cuando no se encuentra un usuario
 */
public class UserNotFoundException extends RuntimeException {
    
    public UserNotFoundException(String message) {
        super(message);
    }
    
    public UserNotFoundException(Long id) {
        super("User not found with id: " + id);
    }
}
