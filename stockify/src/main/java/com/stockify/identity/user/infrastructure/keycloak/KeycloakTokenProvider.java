package com.stockify.identity.user.infrastructure.keycloak;

import com.stockify.identity.user.infrastructure.keycloak.exception.KeycloakException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.time.Instant;

/**
 * Provides a cached Keycloak Admin API access token obtained via the
 * OAuth 2.0 {@code client_credentials} grant.
 *
 * <p>The token is fetched on the first call and reused until it is within
 * {@link #EXPIRY_BUFFER_SECONDS} of its expiry, at which point a fresh token
 * is requested transparently. This avoids per-request token round-trips while
 * still handling expiry correctly.
 *
 * <p>This component is thread-safe under virtual threads: token refresh is
 * cheap and idempotent, so no locking is needed — at worst two concurrent
 * callers both fetch a fresh token simultaneously.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KeycloakTokenProvider {

    /**
     * How many seconds before actual expiry we treat the token as expired,
     * to avoid using a token that expires mid-request.
     */
    private static final int EXPIRY_BUFFER_SECONDS = 30;

    private final RestClient restClient;
    private final KeycloakAdminProperties properties;

    private volatile String cachedToken;
    private volatile Instant tokenExpiresAt = Instant.EPOCH;

    /**
     * Returns a valid Keycloak Admin API access token.
     *
     * <p>If the cached token is still valid (with a {@value #EXPIRY_BUFFER_SECONDS}-second
     * buffer), it is returned immediately. Otherwise a new token is fetched from
     * the Keycloak token endpoint using the {@code client_credentials} grant.
     *
     * @return a non-null, non-expired Bearer token string
     * @throws KeycloakException if the token endpoint returns a non-2xx response
     */
    public @NonNull String getAccessToken() {
        if (this.cachedToken != null && Instant.now().isBefore(this.tokenExpiresAt))
            return this.cachedToken;

        return fetchNewToken();
    }

    private @NonNull String fetchNewToken() {
        log.debug("Fetching new Keycloak admin token from '{}'", properties.tokenUrl());

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "client_credentials");
        form.add("client_id", properties.clientId());
        form.add("client_secret", properties.clientSecret());

        KeycloakTokenResponse response = this.restClient.post()
                .uri(this.properties.tokenUrl())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(form)
                .retrieve()
                .onStatus(status -> !status.is2xxSuccessful(), (req, res) -> {
                    throw new KeycloakException(
                            HttpStatus.valueOf(res.getStatusCode().value()),
                            "Failed to obtain Keycloak admin token: HTTP " + res.getStatusCode()
                    );
                })
                .body(KeycloakTokenResponse.class);

        if (response == null || response.accessToken() == null)
            throw new KeycloakException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Keycloak token endpoint returned an empty response"
            );

        this.cachedToken = response.accessToken();
        this.tokenExpiresAt = Instant.now()
                .plusSeconds(response.expiresIn())
                .minusSeconds(EXPIRY_BUFFER_SECONDS);

        log.debug("New Keycloak admin token cached, expires at '{}'", this.tokenExpiresAt);

        return this.cachedToken;
    }
}
