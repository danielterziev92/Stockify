package com.stockify.identity.domain.verificationcode;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Duration;

/**
 * Validation and configuration constants for the {@link VerificationCode} aggregate.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class VerificationCodeRule {

    /**
     * Validity durations for each {@link VerificationCodeType}.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Expiry {
        public static final Duration EMAIL_VERIFICATION = Duration.ofHours(24);
        public static final Duration PASSWORD_RESET = Duration.ofMinutes(15);
    }

    /**
     * Constraints and error message keys for the verification code value.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Code {
        public static final int LENGTH = 6;
        public static final String EXPIRED_MSG = "verification.code.expired";
        public static final String NOT_FOUND_MSG = "verification.code.not-found";
    }

    /**
     * Default redirect paths used after a verification code is redeemed.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class RedirectUrl {
        public static final String EMAIL_VERIFICATION_REDIRECT = "/verify-email";
        public static final String PASSWORD_RESET_REDIRECT = "/reset-password";
    }
}
