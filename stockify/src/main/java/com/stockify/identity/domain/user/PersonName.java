package com.stockify.identity.domain.user;

import com.stockify.shared.exception.InvalidValueException;
import org.jmolecules.ddd.types.ValueObject;
import org.jspecify.annotations.NonNull;

public record PersonName(@NonNull String value) implements ValueObject {
    public PersonName {
        value = value.trim();
        if (value.isBlank()) throw new InvalidValueException(ProfileRule.PersonName.BLANK_MSG);
        if (value.length() < ProfileRule.PersonName.MIN_LENGTH)
            throw new InvalidValueException(ProfileRule.PersonName.MIN_LENGTH_MSG, value.length());
        if (value.length() > ProfileRule.PersonName.MAX_LENGTH)
            throw new InvalidValueException(ProfileRule.PersonName.MAX_LENGTH_MSG, value.length());
    }
}
