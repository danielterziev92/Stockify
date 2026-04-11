package com.stockify.tenancy.domain.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

/**
 * Compile-time constants governing the behavior of the {@link com.stockify.tenancy.domain.user.User} aggregate.
 *
 * <p>Grouped into nested classes by concern so that call-sites are self-documenting:
 * {@code UserRule.PasswordHash.BCRYPT_LENGTH} is unambiguous,
 * whereas a flat constant {@code BCRYPT_LENGTH} would not be.
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
     * Rules governing the structure and format of a BCrypt password hash.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class PasswordHash {

        /**
         * Expected length of a BCrypt hash in characters.
         * A BCrypt hash has the format {@code $2a$} or {@code $2b$} followed by
         * a two-digit cost factor, {@code $}, and 53 Base64 characters — 60 total.
         */
        public static final int BCRYPT_LENGTH = 60;

        /**
         * Regular expression used to validate the format of a BCrypt hash.
         * Accepts both the {@code $2a$} and {@code $2b$} variants with any two-digit
         * cost factor and a 53-character Base64 payload.
         */
        public static final Pattern BCRYPT_PATTERN = Pattern.compile("^\\$2[ab]\\$\\d{2}\\$[./A-Za-z0-9]{53}$");

        /**
         * Message key used when the provided password hash is blank.
         * Resolved via {@code MessageSource} in the exception handler.
         */
        public static final String BLANK_MSG = "user.password.blank";

        /**
         * Message key used when the provided password hash length
         * does not equal {@link #BCRYPT_LENGTH}.
         * Resolved via {@code MessageSource} in the exception handler.
         */
        public static final String BCRYPT_LENGTH_MSG = "user.password.bcrypt.length";

        /**
         * Message key used when the provided password hash does not match {@link #BCRYPT_PATTERN}.
         * Resolved via {@code MessageSource} in the exception handler.
         */
        public static final String BCRYPT_PATTERN_MSG = "user.password.bcrypt.pattern";
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
