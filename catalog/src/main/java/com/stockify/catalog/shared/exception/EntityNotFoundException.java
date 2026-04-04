package com.stockify.catalog.shared.exception;

public class EntityNotFoundException extends DomainException {
    public EntityNotFoundException(String messageCode, Object... args) {
        super(messageCode, args);
    }
}
