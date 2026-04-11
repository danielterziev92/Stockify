package com.stockify.shared.exception;

/**
 * Thrown when a requested entity cannot be found in the system.
 *
 * <p>Typically raised by repository or service layer lookups when no record
 * matches the given identifier, and the absence of the entity is considered
 * an error rather than an expected empty result.
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * Constructs a new exception with the given detail message.
     *
     * @param message a human-readable description of which entity was not found
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
