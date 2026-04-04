package com.stockify.catalog.shared.exception;

/**
 * Thrown when a business invariant is violated.
 * Maps to HTTP 409 Conflict.
 */
public class BusinessRuleException extends DomainException {

    public BusinessRuleException(String messageCode, Object... args) {
        super(messageCode, args);
    }
}