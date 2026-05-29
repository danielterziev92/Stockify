package com.stockify.identity.user.infrastructure.keycloak.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Partial representation of a Keycloak user returned by the Admin REST API.
 *
 * <p>Only the fields required by Stockify are mapped — all others are ignored
 * via {@link JsonIgnoreProperties}.
 *
 * @param id    the Keycloak-assigned user UUID
 * @param email the user's email address
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record KeycloakUserRepresentation(String id, String email) {
}
