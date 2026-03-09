package com.tecsup.app.micro.delivery.domain.exception;

public class InvalidDeliveryException extends RuntimeException {
    public InvalidDeliveryException(String message) {
        super(message);
    }
}
