package com.stockify.identity.otp.application.command;

import org.jspecify.annotations.NonNull;

/**
 * Command carrying the input required to resend a one-time password.
 *
 * @param email the email address of the user requesting a new OTP
 */
public record ResendOtpCommand(@NonNull String email) {
}
