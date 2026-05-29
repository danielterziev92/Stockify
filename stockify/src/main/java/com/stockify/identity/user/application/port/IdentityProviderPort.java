package com.stockify.identity.user.application.port;

import com.stockify.shared.vo.UserId;
import org.jspecify.annotations.NonNull;

/**
 * Port defining the contract for external identity provider operations.
 *
 * <p>Abstracts all communication with the identity provider so that the
 * application layer remains decoupled from any specific implementation.
 * The infrastructure layer is responsible for providing a concrete implementation
 * (e.g., Keycloak, Auth0).
 */
public interface IdentityProviderPort {


    /**
     * Looks up a user by their email address and returns their identifier.
     *
     * @param email the email address to search for
     * @return the {@link UserId} of the matching user
     * @throws com.stockify.shared.exception.EntityNotFoundException if no user
     *                                                              exists with the given email
     */
    @NonNull UserId findUserIdByEmail(@NonNull String email);

    /**
     * Triggers a password reset flow for the given user in the identity provider.
     *
     * <p>The identity provider is responsible for generating the reset token,
     * dispatching the reset email, and handling the credential update.
     *
     * @param userId the identifier of the user requesting a password reset
     */
    void sendPasswordResetEmail(@NonNull UserId userId);

    /**
     * Creates a new user account in the identity provider.
     *
     * @param email    the email address of the new user
     * @param password the plain-text password of the new user
     * @return the {@link UserId} assigned by the identity provider
     * @throws com.stockify.shared.exception.BusinessRuleException if the email
     *                                                             is already registered
     */
    @NonNull UserId createUser(@NonNull String email, @NonNull String password);

    /**
     * Marks the user's email address as verified in the identity provider.
     *
     * @param userId the identifier of the user whose email should be verified
     */
    void verifyUserEmail(@NonNull UserId userId);

    /**
     * Deletes a user account from the identity provider.
     *
     * <p>Used as a compensating transaction when a downstream operation fails
     * after the user has already been created.
     *
     * @param userId the identifier of the user to delete
     */
    void deleteUser(@NonNull UserId userId);
}
