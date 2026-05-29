package com.stockify.identity.user.application.command;

import org.jmolecules.architecture.cqrs.Command;
import org.jspecify.annotations.NonNull;

/**
 * Command carrying the input required to register a new user account.
 *
 * @param email    the raw email address provided by the registering user
 * @param password the raw plain-text password provided by the registering user
 */
@Command
public record RegisterUserCommand(
        @NonNull String email,
        @NonNull String password
) {
}
