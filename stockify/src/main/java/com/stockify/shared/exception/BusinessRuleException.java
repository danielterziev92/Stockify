package com.stockify.shared.exception;

/**
 * Thrown when an operation violates a business rule or domain policy.
 *
 * <p>Unlike {@link InvalidValueException}, which signals that a value itself is
 * illegal, this exception represents a higher-level constraint — for example,
 * attempting to activate an already-active account, or placing an order when
 * stock is insufficient.
 */
public class BusinessRuleException extends DomainException {

    /**
     * Constructs a new exception with the given message code and optional arguments.
     *
     * @param messageCode the key used to look up the localized error message template
     * @param args        optional arguments interpolated into the message template
     */
    public BusinessRuleException(String messageCode, Object... args) {
        super(messageCode, args);
    }
}
