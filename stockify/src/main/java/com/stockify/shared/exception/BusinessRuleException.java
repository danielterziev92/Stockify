package com.stockify.shared.exception;

/**
 * Thrown when an operation violates a business rule or domain policy.
 *
 * <p>Unlike {@link InvalidValueException}, which signals that a value itself is
 * illegal, this exception represents a higher-level constraint — for example,
 * attempting to activate an already-active account, or placing an order when
 * stock is insufficient.
 */
public class BusinessRuleException extends RuntimeException {

    /**
     * Constructs a new exception with the given detail message.
     *
     * @param message a human-readable description of the violated business rule
     */
    public BusinessRuleException(String message) {
        super(message);
    }
}
