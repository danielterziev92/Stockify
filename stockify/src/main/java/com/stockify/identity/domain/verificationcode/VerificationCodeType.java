package com.stockify.identity.domain.verificationcode;

import java.time.Duration;

/**
 * Classifies the purpose of a {@link VerificationCode} and determines its expiry duration.
 */
public enum VerificationCodeType {

    /**
     * Confirms ownership of an email address during registration.
     */
    EMAIL_VERIFICATION {
        @Override
        public Duration expiry() {
            return VerificationCodeRule.Expiry.EMAIL_VERIFICATION;
        }
    },

    /**
     * Authorizes a password reset flow.
     */
    PASSWORD_RESET {
        @Override
        public Duration expiry() {
            return VerificationCodeRule.Expiry.PASSWORD_RESET;
        }
    };

    /**
     * Returns the validity duration for this verification code type.
     */
    public abstract Duration expiry();
}
