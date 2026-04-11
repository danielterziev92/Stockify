package com.stockify.shared.exception;

/**
 * Thrown when a requested entity cannot be found in the system.
 *
 * <p>Typically raised by repository or service layer lookups when no record
 * matches the given identifier, and the absence of the entity is considered
 * an error rather than an expected empty result.
 */
public class EntityNotFoundException extends DomainException {

    /**
     * Constructs a new exception with the given message code and optional arguments.
     *
     * @param messageCode the key used to look up the localized error message template
     * @param args        optional arguments interpolated into the message template
     */
    public EntityNotFoundException(String messageCode, Object... args) {
        super(messageCode, args);
    }
}
