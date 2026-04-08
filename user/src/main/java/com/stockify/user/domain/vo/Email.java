package com.stockify.user.domain.vo;

import com.stockify.shared.exception.InvalidValueException;
import com.stockify.user.domain.rule.UserRule;
import org.jmolecules.ddd.types.ValueObject;
import org.jspecify.annotations.NonNull;

/**
 * Value object representing a validated and normalised email address.
 *
 * <p>On construction, the raw input is trimmed and lowercased before validation,
 * ensuring that two {@code Email} instances constructed from semantically equal
 * addresses are always equal by value.
 *
 * <p>Validation rules:
 * <ul>
 *   <li>Must not be blank after trimming.</li>
 *   <li>Must match the RFC 5322 pattern defined in {@link UserRule.Email#PATTERN}.</li>
 *   <li>Must not exceed {@link UserRule.Email#MAX_LENGTH} characters.</li>
 * </ul>
 *
 * @param value the normalised (trimmed and lowercased) email address
 */
public record Email(@NonNull String value) implements ValueObject {

    /**
     * Canonical constructor — normalises and validates the raw input.
     *
     * @throws InvalidValueException if the value is blank after normalisation
     *                               or does not match the expected email format
     */
    public Email {
        value = value.toLowerCase().trim();

        if (value.isBlank())
            throw new InvalidValueException(UserRule.Email.BLANK_MSG);
        if (!UserRule.Email.PATTERN.matcher(value).matches())
            throw new InvalidValueException(UserRule.Email.INVALID_MSG, value);
    }

    /**
     * Factory method for constructing an {@code Email} from a raw string.
     * Delegates to the canonical constructor — normalisation and validation
     * are applied automatically.
     *
     * @param value the raw email address string
     * @return a validated and normalised {@code Email}
     * @throws InvalidValueException if {@code value} is blank or invalid
     */
    public static @NonNull Email of(@NonNull String value) {
        return new Email(value);
    }
}
