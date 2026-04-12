package com.stockify.identity.domain.profile;

import org.jmolecules.ddd.types.ValueObject;
import org.jspecify.annotations.NonNull;

public record PhoneNumber(@NonNull String value) implements ValueObject {
    public PhoneNumber {
        PhoneValidationStrategyRegistry.resolve(value);
    }
}
