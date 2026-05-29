package com.stockify.identity.user.infrastructure.keycloak;

import com.stockify.identity.infrastructure.keycloak.KeycloakTokenProvider;
import com.stockify.identity.infrastructure.keycloak.dto.KeycloakCreateUserRequest;
import com.stockify.identity.user.application.port.IdentityProviderPort;
import com.stockify.identity.user.infrastructure.keycloak.exception.KeycloakException;
import com.stockify.shared.exception.EntityNotFoundException;
import com.stockify.shared.vo.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

/**
 * Keycloak implementation of {@link IdentityProviderPort}.
 *
 * <p>Encapsulates all HTTP communication with the Keycloak Admin REST API.
 * No other class in the application needs to know about the Admin API's
 * URL structure, authentication headers, or response format.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KeycloakAdminClient implements IdentityProviderPort {

    private final RestClient restClient;
    private final KeycloakAdminProperties properties;
    private final KeycloakTokenProvider tokenProvider;

    /**
     * {@inheritDoc}
     *
     * <p>Queries the Admin API with an exact email filter and returns the first match.
     *
     * @throws EntityNotFoundException if no user exists with the given email
     */
    @Override
    public @NonNull UserId findUserIdByEmail(@NonNull String email) {
        log.debug("Looking up Keycloak user by email '{}'", email);

        List<KeycloakUserRepresentation> users = this.restClient.get()
                .uri("%s/users?email=%s&exact=true".formatted(this.properties.realmAdminUrl(), email))
                .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(this.tokenProvider.getAccessToken()))
                .retrieve()
                .onStatus(status -> !status.is2xxSuccessful(), (req, res) -> {
                    throw new KeycloakException(
                            res.getStatusCode(),
                            "Failed to lookup Keycloak user by email: HTTP " + res.getStatusCode()
                    );
                })
                .body(new ParameterizedTypeReference<>() {
                });

        if (users == null || users.isEmpty()) {
            throw new EntityNotFoundException("user.not-found", email);
        }

        return UserId.of(users.getFirst().id());
    }

    /**
     * {@inheritDoc}
     *
     * <p>Uses the Keycloak Admin API to trigger the built-in
     * {@code UPDATE_PASSWORD} required action email for the given user.
     */
    @Override
    public void sendPasswordResetEmail(@NonNull UserId userId) {
        log.debug("Sending password reset email for Keycloak user '{}'", userId.value());

        this.restClient.put()
                .uri("%s/users/%s/execute-actions-email".formatted(this.properties.realmAdminUrl(), userId.value()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(this.tokenProvider.getAccessToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(List.of("UPDATE_PASSWORD"))
                .retrieve()
                .onStatus(status -> status.value() == HttpStatus.NOT_FOUND.value(), (req, res) -> {
                    throw new KeycloakException(HttpStatus.NOT_FOUND, "Keycloak user not found: " + userId.value());
                })
                .onStatus(status -> !status.is2xxSuccessful(), (req, res) -> {
                    throw new KeycloakException(res.getStatusCode(),
                            "Failed to send password reset email: HTTP " + res.getStatusCode());
                })
                .toBodilessEntity();

        log.info("Password reset email sent for Keycloak user '{}'", userId.value());
    }

    /**
     * {@inheritDoc}
     *
     * <p>The Keycloak user ID is extracted from the {@code Location} response
     * header ({@code /admin/realms/{realm}/users/{id}}).
     *
     * @throws KeycloakException with status 409 if the email is already registered
     */
    @Override
    public @NonNull UserId createUser(@NonNull String email, @NonNull String password) {
        log.debug("Creating Keycloak user with email {}", email);

        KeycloakCreateUserRequest request = KeycloakCreateUserRequest.of(email, password);

        ResponseEntity<Void> response = this.restClient.post()
                .uri("%s%s".formatted(this.properties.realmAdminUrl(), "/users"))
                .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(this.tokenProvider.getAccessToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .onStatus(status -> status.value() == 409, (req, res) -> {
                    throw new KeycloakException(HttpStatus.CONFLICT, "Email already registered: " + email);
                })
                .onStatus(status -> !status.is2xxSuccessful(), (req, res) -> {
                    throw new KeycloakException(
                            res.getStatusCode(),
                            "Failed to create Keycloak user: HTTP " + res.getStatusCode()
                    );
                })
                .toBodilessEntity();

        String location = response.getHeaders().getFirst(HttpHeaders.LOCATION);
        if (location == null)
            throw new KeycloakException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Keycloak did not return a Location header after user creation"
            );

        String userId = location.substring(location.lastIndexOf('/') + 1);
        log.info("Keycloak user created with id '{}' for email '{}'", userId, email);

        return UserId.of(userId);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Sets {@code emailVerified = true} and clears all required actions
     * so the user can log in without further prompts.
     */
    @Override
    public void verifyUserEmail(@NonNull UserId userId) {
        log.debug("Marking email as verified for Keycloak user '{}'", userId.value());

        Map<String, Object> body = Map.of(
                "emailVerified", true,
                "requiredActions", new String[]{}
        );

        this.restClient.put()
                .uri("%s/users/%s".formatted(this.properties.realmAdminUrl(), userId.value()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(this.tokenProvider.getAccessToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .onStatus(status -> status.value() == 404, (req, res) -> {
                    throw new KeycloakException(HttpStatus.NOT_FOUND, "Keycloak user not found: %s".formatted(userId.value()));
                })
                .onStatus(status -> !status.is2xxSuccessful(), (req, res) -> {
                    throw new KeycloakException(
                            res.getStatusCode(),
                            "Failed to verify Keycloak user email: HTTP %s".formatted(res.getStatusCode())
                    );
                })
                .toBodilessEntity();

        log.info("Email verified in Keycloak for user '{}'", userId.value());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteUser(@NonNull UserId userId) {
        log.warn("Deleting Keycloak user '{}' (compensating transaction)", userId.value());

        this.restClient.delete()
                .uri("%s/users/%s".formatted(properties.realmAdminUrl(), userId.value()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(this.tokenProvider.getAccessToken()))
                .retrieve()
                .onStatus(status -> status.value() == 404, (req, res) -> {
                    throw new KeycloakException(HttpStatus.NOT_FOUND, "Keycloak user not found: %s".formatted(userId.value()));
                })
                .onStatus(status -> !status.is2xxSuccessful(), (req, res) -> {
                    throw new KeycloakException(
                            res.getStatusCode(),
                            "Failed to delete Keycloak user '%s': HTTP %s".formatted(userId.value(), res.getStatusCode())
                    );
                })
                .toBodilessEntity();

        log.info("Keycloak user '{}' deleted", userId.value());
    }
}
