package com.stockify.identity.otp.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Duration;

/**
 * Compile-time constants governing the behavior of the {@link Otp} aggregate.
 *
 * <p>Grouped into nested classes by concern so that call-sites are self-documenting:
 * {@code OtpRule.Code.LENGTH} is unambiguous, whereas a flat constant
 * {@code LENGTH} would not be.
 *
 * <p>This class is a utility holder and cannot be instantiated.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OtpRule {

    /**
     * Rules governing OTP expiry durations per use case.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Expiry {

        /**
         * How long an email verification OTP remains valid after a generation.
         */
        public static final Duration EMAIL_VERIFICATION = Duration.ofHours(1);

        /**
         * Message key used when an OTP is submitted after its expiry time.
         */
        public static final String EXPIRED_MSG = "otp.expired";
    }

    /**
     * Rules governing the structure and lookup of OTP codes.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Code {

        /**
         * Number of digits in a generated OTP code.
         */
        public static final int LENGTH = 8;

        /**
         * Upper bound (exclusive) used when generating a random numeric OTP code.
         *
         * <p>Equals {@code 10^LENGTH}, ensuring the generated value always fits
         * within {@link #LENGTH} digits when zero-padded.
         */
        public static final int UPPER_BOUND = 100_000_000;

        /**
         * Message key used when no OTP record is found for the given identifier.
         */
        public static final String NOT_FOUND_MSG = "otp.not-found";
    }
}
