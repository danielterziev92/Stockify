package com.stockify.catalog.shared.exception;

/**
 * Thrown when a Value Object receives an invalid input.
 * Maps to HTTP 422 Unprocessable Entity.
 */
public class InvalidValueException extends DomainException {
    public InvalidValueException(String messageCode, Object... args) {
        super(messageCode, args);
    }
}
