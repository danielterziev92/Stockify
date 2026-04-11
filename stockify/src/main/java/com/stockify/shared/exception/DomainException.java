package com.stockify.shared.exception;

import lombok.Getter;

/**
 * Base class for all domain-layer exceptions in the application.
 *
 * <p>Subclasses represent specific domain rule violations and carry a {@code messageCode}
 * that maps to a localized message template, along with optional arguments used to
 * interpolate that template at the presentation layer.
 *
 * <p>Being a {@link RuntimeException}, domain exceptions are unchecked and are expected
 * to bubble up to the application boundary where they can be translated into
 * user-facing error responses.
 */
@Getter
public abstract class DomainException extends RuntimeException {

    /**
     * Message key used to resolve a localized error message.
     */
    private final String messageCode;

    /**
     * Arguments interpolated into the resolved message template.
     */
    private final Object[] args;

    /**
     * Constructs a new domain exception with the given message code and optional arguments.
     *
     * @param messageCode the key used to look up the localized message template
     * @param args        optional arguments interpolated into the message template
     */
    protected DomainException(String messageCode, Object... args) {
        super(messageCode);
        this.messageCode = messageCode;
        this.args = args;
    }
}
