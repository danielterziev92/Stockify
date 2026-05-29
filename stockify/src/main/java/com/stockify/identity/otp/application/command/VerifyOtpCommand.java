package com.stockify.identity.otp.application.command;

import org.jspecify.annotations.NonNull;

/**
 * Command carrying the input required to verify a one-time password.
 *
 * @param email the email address of the user submitting the OTP
 * @param code  the 8-digit numeric OTP code submitted by the user
 */
public record VerifyOtpCommand(
        @NonNull String email,
        @NonNull String code
) {
}
