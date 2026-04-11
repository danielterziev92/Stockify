package com.stockify.shared.exception;

/**
 * Thrown when a value object or domain primitive is constructed with an illegal value.
 *
 * <p>Use this exception to signal that a given input violates a domain invariant —
 * for example, a negative price, a blank name, or an out-of-range quantity.
 * The {@code messageCode} and {@code args} are forwarded to {@link DomainException}
 * for localized message resolution at the presentation layer.
 */
public class InvalidValueException extends DomainException {

    /**
     * Constructs a new exception with the given message code and optional arguments.
     *
     * @param messageCode the key used to look up the localized error message template
     * @param args        optional arguments interpolated into the message template
     */
    public InvalidValueException(String messageCode, Object... args) {
        super(messageCode, args);
    }
}
