package com.stockify.identity.otp.domain;

import java.time.Duration;

/**
 * Classifies the purpose of an {@link Otp} and provides its expiry duration.
 *
 * <p>Each constant overrides {@link #expiry()} to return the TTL defined in
 * {@link OtpRule.Expiry} for that use case, keeping expiry policy centralized
 * in {@link OtpRule} and the dispatch logic here.
 */
public enum OtpType {

    /**
     * OTP sent to verify a user's email address after registration.
     *
     * <p>Expires after {@link OtpRule.Expiry#EMAIL_VERIFICATION}.
     */
    EMAIL_VERIFICATION {
        @Override
        public Duration expiry() {
            return OtpRule.Expiry.EMAIL_VERIFICATION;
        }
    };

    /**
     * Returns the validity duration for this OTP type.
     *
     * @return the TTL as a {@link Duration}
     */
    public abstract Duration expiry();
}
