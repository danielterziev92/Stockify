package com.stockify.identity.user.application.command;

import org.jmolecules.architecture.cqrs.Command;
import org.jspecify.annotations.NonNull;

/**
 * Command carrying the input required to resend a one-time password.
 *
 * @param email the email address of the user requesting a new OTP
 */
@Command
public record ResendOtpCommand(@NonNull String email) {
}
