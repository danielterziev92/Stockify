package com.stockify.identity.user.domain;

import com.stockify.shared.exception.InvalidValueException;
import org.jmolecules.ddd.types.ValueObject;
import org.jspecify.annotations.NonNull;

/**
 * Value object representing a validated plain-text password.
 *
 * <p>Enforces all password strength rules defined in {@link UserRule.Password}.
 * Each constraint is checked independently so that the caller receives a specific
 * message code for the first violation encountered, rather than a generic error.
 *
 * <p><strong>Note:</strong> this value object holds the plain-text password only
 * long enough to validate it and forward it to Keycloak during registration or a
 * password change. It is never persisted — Keycloak is the authoritative store
 * for credentials.
 *
 * @param value the validated plain-text password
 */
public record Password(@NonNull String value) implements ValueObject {

    /**
     * Compact constructor — trims and validates the password against all
     * strength rules defined in {@link UserRule.Password}.
     *
     * @throws InvalidValueException if any constraint is violated; the exception
     *                               carries the specific message code for the failing rule
     */
    public Password {
        value = value.trim();

        if (value.isBlank())
            throw new InvalidValueException(UserRule.Password.BLANK_MSG);

        if (value.length() < UserRule.Password.MIN_LENGTH)
            throw new InvalidValueException(UserRule.Password.MIN_LENGTH_MSG, UserRule.Password.MIN_LENGTH);

        if (value.length() > UserRule.Password.MAX_LENGTH)
            throw new InvalidValueException(UserRule.Password.MAX_LENGTH_MSG, UserRule.Password.MAX_LENGTH);

        if (!UserRule.Password.UPPERCASE.matcher(value).matches())
            throw new InvalidValueException(UserRule.Password.UPPERCASE_MSG);

        if (!UserRule.Password.LOWERCASE.matcher(value).matches())
            throw new InvalidValueException(UserRule.Password.LOWERCASE_MSG);

        if (!UserRule.Password.DIGIT.matcher(value).matches())
            throw new InvalidValueException(UserRule.Password.DIGIT_MSG);

        if (!UserRule.Password.SPECIAL.matcher(value).matches())
            throw new InvalidValueException(UserRule.Password.SPECIAL_MSG);
    }

    /**
     * Factory method for creating a {@code Password} from a raw string.
     *
     * @param value the raw password input
     * @return a new, validated {@code Password} instance
     */
    public static @NonNull Password of(@NonNull String value) {
        return new Password(value);
    }
}
