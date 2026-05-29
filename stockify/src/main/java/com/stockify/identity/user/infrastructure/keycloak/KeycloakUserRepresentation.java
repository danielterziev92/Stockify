package com.stockify.identity.user.infrastructure.keycloak;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jspecify.annotations.NonNull;

/**
 * Partial representation of a Keycloak user returned by the Admin REST API.
 *
 * <p>Only the fields required by Stockify are mapped — all others are ignored
 * via {@link JsonIgnoreProperties}. The {@code emailVerified} flag is used to
 * derive the {@link com.stockify.identity.user.domain.UserStatus} of the account:
 * {@code true} maps to {@code ACTIVE}, {@code false} to {@code PENDING_VERIFICATION}.
 *
 * @param id            the Keycloak-assigned user UUID
 * @param email         the user's email address
 * @param emailVerified whether the user's email address has been verified
 */
@JsonIgnoreProperties(ignoreUnknown = true)
record KeycloakUserRepresentation(
        @NonNull String id,
        @NonNull String email,
        @NonNull Boolean emailVerified
) {}
