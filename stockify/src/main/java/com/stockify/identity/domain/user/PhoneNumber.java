package com.stockify.identity.domain.user;

import com.stockify.identity.domain.user.validation.phone.PhoneValidationStrategyRegistry;
import org.jmolecules.ddd.types.ValueObject;
import org.jspecify.annotations.NonNull;

public record PhoneNumber(@NonNull String value) implements ValueObject {
    public PhoneNumber {
        PhoneValidationStrategyRegistry.resolve(value);
    }
}
