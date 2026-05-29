package com.stockify.identity.user.infrastructure.keycloak.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

/**
 * Thrown when a Keycloak Admin REST API call fails.
 *
 * <p>Wraps the HTTP status code and a descriptive message returned by
 * the Keycloak server so that the caller can decide how to handle or
 * translate the error (e.g., map a 409 to a domain-level duplicate-email
 * exception).
 */
@Getter
public class KeycloakException extends RuntimeException {

    /**
     * The HTTP status code returned by Keycloak.
     */
    private final HttpStatusCode statusCode;

    /**
     * Constructs a new exception with the given status code and message.
     *
     * @param statusCode the HTTP status returned by Keycloak
     * @param message    a human-readable description of the error
     */
    public KeycloakException(HttpStatusCode statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}
