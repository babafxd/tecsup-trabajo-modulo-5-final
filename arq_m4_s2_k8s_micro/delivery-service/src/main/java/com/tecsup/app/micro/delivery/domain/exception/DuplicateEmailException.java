package com.tecsup.app.micro.delivery.domain.exception;

/**
 * Excepción cuando se intenta crear un usuario con un email que ya existe
 */
public class DuplicateEmailException extends RuntimeException {
    
    public DuplicateEmailException(String email) {
        super("Email already exists: " + email);
    }
}
