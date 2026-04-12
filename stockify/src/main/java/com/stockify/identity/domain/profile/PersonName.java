package com.stockify.identity.domain.profile;

import com.stockify.shared.exception.InvalidValueException;
import org.jmolecules.ddd.types.ValueObject;
import org.jspecify.annotations.NonNull;

/**
 * Value object representing a person's name (first or last).
 *
 * <p>Enforces length and non-blank constraints defined in {@link ProfileRule.PersonName}.
 * Leading and trailing whitespace is trimmed before validation.
 *
 * @param value the name string
 */
public record PersonName(@NonNull String value) implements ValueObject {

    /**
     * Compact constructor that trims and validates the name.
     *
     * @throws InvalidValueException if the value is blank or outside the allowed length range
     */
    public PersonName {
        value = value.trim();
        if (value.isBlank()) throw new InvalidValueException(ProfileRule.PersonName.BLANK_MSG);
        if (value.length() < ProfileRule.PersonName.MIN_LENGTH)
            throw new InvalidValueException(ProfileRule.PersonName.MIN_LENGTH_MSG, value.length());
        if (value.length() > ProfileRule.PersonName.MAX_LENGTH)
            throw new InvalidValueException(ProfileRule.PersonName.MAX_LENGTH_MSG, value.length());
    }
}
