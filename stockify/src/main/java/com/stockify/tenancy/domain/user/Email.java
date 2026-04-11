package com.stockify.tenancy.domain.user;

import com.stockify.shared.exception.InvalidValueException;
import org.jmolecules.ddd.types.ValueObject;
import org.jspecify.annotations.NonNull;

/**
 * Value object representing a valid, normalized email address.
 *
 * <p>On construction the raw input is trimmed and lower-cased, then validated
 * against {@link UserRule.Email#PATTERN}. Any violation throws a
 * {@link InvalidValueException} with the appropriate message code.
 *
 * @param value the normalized (trimmed, lower-cased) email address string
 */
public record Email(@NonNull String value) implements ValueObject {

    /**
     * Compact constructor — normalizes and validates the email address.
     *
     * @throws InvalidValueException if {@code value} is blank, exceeds
     *                               {@link UserRule.Email#MAX_LENGTH} characters,
     *                               or does not match {@link UserRule.Email#PATTERN}
     */
    public Email {
        value = value.trim().toLowerCase();

        if (value.isBlank()) throw new InvalidValueException(UserRule.Email.BLANK_MSG);
        if (value.length() > UserRule.Email.MAX_LENGTH)
            throw new InvalidValueException(UserRule.Email.LENGTH_MSG, value);
        if (!UserRule.Email.PATTERN.matcher(value).matches())
            throw new InvalidValueException(UserRule.Email.INVALID_MSG, value);
    }

    /**
     * Factory method for creating an {@code Email} from a raw string.
     *
     * @param value the raw email address input
     * @return a new, validated {@code Email} instance
     */
    public static @NonNull Email of(@NonNull String value) {
        return new Email(value);
    }
}
