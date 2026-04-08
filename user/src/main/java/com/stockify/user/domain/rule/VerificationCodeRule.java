package com.stockify.user.domain.rule;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Compile-time constants governing the behavior of the {@link com.stockify.user.domain.VerificationCode} aggregate.
 *
 * <p>Grouped into nested classes by concern so that call-sites are self-documenting:
 * {@code VerificationCodeRule.ExpireAt.DURATION_IN_SECONDS} is unambiguous,
 * whereas a flat constant {@code DURATION_IN_SECONDS} would not be.
 *
 * <p>This class is a utility holder and cannot be instantiated.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class VerificationCodeRule {

    /**
     * Rules governing the structure of the generated numeric code.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Code {
        /**
         * The exact number of digits in a generated verification code.
         * A value of {@code 8} produces codes in the range [10_000_000, 99_999_999].
         */
        public static final int LENGTH = 8;
    }

    /**
     * Rules governing code expiry.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class ExpireAt {
        /**
         * The number of seconds a verification code remains valid after generation.
         * Defaults to {@code 3600} (one hour).
         */
        public static final int DURATION_IN_SECONDS = 3600;

        /**
         * Message key used when a verification attempt is made on an expired code.
         * Resolved via {@code MessageSource} in the exception handler.
         */
        public static final String EXPIRED_MSG = "verification.code.expired";
    }

    /**
     * Redirect URLs forwarded through domain events to the notification service.
     * These are presentation-layer concerns and are never persisted.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class RedirectUrl {
        /**
         * The URL embedded in the verification email link.
         * The user is sent here when they click the link to submit their code.
         */
        public static final String AFTER_CREATION = "/verify";

        /**
         * The URL the client is redirected to after successful verification.
         */
        public static final String AFTER_VERIFICATION = "/company/detail";
    }
}
