package com.stockify.identity.user.infrastructure.keycloak.dto;

import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Map;

/**
 * Request body sent to the Keycloak Admin REST API when creating a new user.
 *
 * <p>Maps to the Keycloak {@code UserRepresentation} JSON format. Only the
 * fields required for initial registration are populated — Keycloak ignores
 * unknown fields on the writing side.
 *
 * <p>The password is embedded as a {@code PasswordCredentialRepresentation}
 * inside the {@code credentials} list. {@code temporary = false} means the
 * user is not forced to change it on first login.
 *
 * @param username      the username (set equal to the email address)
 * @param email         the user's email address
 * @param enabled       whether the account is enabled immediately after creation
 * @param emailVerified initial email-verified state — always {@code false} until
 *                      the OTP flow completes
 * @param credentials   the initial password credential
 */
public record KeycloakCreateUserRequest(
        @NonNull String username,
        @NonNull String email,
        boolean enabled,
        boolean emailVerified,
        List<Map<String, Object>> credentials
) {

    /**
     * Builds a {@code KeycloakCreateUserRequest} for a new registration.
     *
     * <p>The account is enabled immediately but {@code emailVerified} is set to
     * {@code false} — the OTP verification flow is responsible for flipping it
     * to {@code true} via
     * {@link com.stockify.identity.user.application.port.IdentityProviderPort#verifyUserEmail}.
     *
     * @param email    the user's email address; used as both {@code username} and {@code email}
     * @param password the plain-text password; wrapped in a non-temporary credential entry
     * @return a fully populated request object ready to be serialized and sent
     */
    public static @NonNull KeycloakCreateUserRequest of(
            @NonNull String email,
            @NonNull String password
    ) {
        return new KeycloakCreateUserRequest(
                email,
                email,
                true,
                false,
                List.of(Map.of("type", "password", "value", password, "temporary", false))
        );
    }
}
