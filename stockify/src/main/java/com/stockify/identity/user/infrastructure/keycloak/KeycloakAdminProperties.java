package com.stockify.identity.user.infrastructure.keycloak;

import org.jspecify.annotations.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for the Keycloak Admin REST API client.
 *
 * <p>Bound from the {@code keycloak.admin} prefix in application configuration.
 * All values are required unless otherwise noted.
 *
 * @param baseUrl      the base URL of the Keycloak server (e.g. {@code http://localhost:8180})
 * @param realm        the target realm name (e.g. {@code stockify})
 * @param clientId     the service account client ID used to get admin tokens
 * @param clientSecret the service account client secret
 */
@ConfigurationProperties(prefix = "keycloak.admin")
public record KeycloakAdminProperties(
        @NonNull String baseUrl,
        @NonNull String realm,
        @NonNull String clientId,
        @NonNull String clientSecret
) {

    /**
     * Returns the base URL for Admin REST API calls scoped to the configured realm.
     *
     * @return the realm-scoped admin API base URL
     */
    public @NonNull String realmAdminUrl() {
        return "%s/admin/realms/%s".formatted(baseUrl, realm);
    }

    /**
     * Returns the token endpoint URL for the configured realm.
     * <p>Used by the token provider to get a service-account access token
     * via the {@code client_credentials} grant.
     *
     * @return the OIDC token endpoint URL
     */
    public @NonNull String tokenUrl() {
        return "%s/realms/%s/protocol/openid-connect/token".formatted(baseUrl, realm);
    }
}
