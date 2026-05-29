package com.stockify.identity.user.infrastructure.keycloak;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Maps the JSON response from the Keycloak {@code /token} endpoint.
 *
 * <p>Only {@code access_token} and {@code expires_in} are needed — all other
 * fields (refresh token, token type, scope) are ignored.
 *
 * @param accessToken the Bearer token to use in Admin API requests
 * @param expiresIn   the token's validity period in seconds
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record KeycloakTokenResponse(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("expires_in") long expiresIn
) {
}
