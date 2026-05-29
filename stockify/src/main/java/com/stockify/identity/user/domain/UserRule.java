package com.stockify.identity.user.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

/**
 * Compile-time constants governing the behavior of the {@link User} aggregate.
 *
 * <p>This class is a utility holder and cannot be instantiated.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserRule {

    /**
     * Rules governing the structure and format of a user's email address.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Email {
        /**
         * Maximum allowed length of an email address in characters.
         * Defined by RFC 5321.
         */
        public static final int MAX_LENGTH = 254;

        /**
         * Regular expression used to validate the format of an email address.
         * Based on RFC 5322, allowing standard local parts and domain labels
         * with a top-level domain between 2 and 63 characters.
         */
        public static final Pattern PATTERN =
                Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,63}$");

        /**
         * Message key used when the provided email address is blank.
         * Resolved via {@code MessageSource} in the exception handler.
         */
        public static final String BLANK_MSG = "user.email.blank";

        /**
         * Message key used when the provided email address exceeds {@link #MAX_LENGTH} characters.
         * Resolved via {@code MessageSource} in the exception handler.
         */
        public static final String LENGTH_MSG = "user.email.length";

        /**
         * Message key used when the provided email address does not match {@link #PATTERN}.
         * Resolved via {@code MessageSource} in the exception handler.
         */
        public static final String INVALID_MSG = "user.email.invalid";
    }

    /**
     * Rules governing the structure and strength of a user's password.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Password {

        /** Minimum allowed password length in characters. */
        public static final int MIN_LENGTH = 8;

        /** Maximum allowed password length in characters. */
        public static final int MAX_LENGTH = 128;

        /** Pattern requiring at least one uppercase letter (A - Z). */
        public static final Pattern UPPERCASE = Pattern.compile(".*[A-Z].*");

        /** Pattern requiring at least one lowercase letter (a - z). */
        public static final Pattern LOWERCASE = Pattern.compile(".*[a-z].*");

        /** Pattern requiring at least one digit (0–9). */
        public static final Pattern DIGIT = Pattern.compile(".*[0-9].*");

        /** Pattern requiring at least one special character. */
        public static final Pattern SPECIAL = Pattern.compile(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*");

        /** Message key used when the provided password is blank. */
        public static final String BLANK_MSG = "user.password.blank";

        /** Message key used when the password is shorter than {@link #MIN_LENGTH}. */
        public static final String MIN_LENGTH_MSG = "user.password.min-length";

        /** Message key used when the password exceeds {@link #MAX_LENGTH}. */
        public static final String MAX_LENGTH_MSG = "user.password.max-length";

        /** Message key used when the password contains no uppercase letter. */
        public static final String UPPERCASE_MSG = "user.password.uppercase";

        /** Message key used when the password contains no lowercase letter. */
        public static final String LOWERCASE_MSG = "user.password.lowercase";

        /** Message key used when the password contains no digit. */
        public static final String DIGIT_MSG = "user.password.digit";

        /** Message key used when the password contains no special character. */
        public static final String SPECIAL_MSG = "user.password.special";
    }

    /**
     * Rules governing user account status transitions and guards.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Status {

        /**
         * Message key used when a status-sensitive operation is attempted
         * on an account that is pending email activation.
         * Resolved via {@code MessageSource} in the exception handler.
         */
        public static final String PENDING_ACTIVATION_MSG = "user.status.pending.activation";

        /**
         * Message key used when a status-sensitive operation is attempted
         * on an inactive account.
         * Resolved via {@code MessageSource} in the exception handler.
         */
        public static final String INACTIVE_MSG = "user.status.inactive";
    }
}
