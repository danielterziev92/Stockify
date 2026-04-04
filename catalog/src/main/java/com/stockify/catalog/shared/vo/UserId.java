package com.stockify.catalog.shared.vo;

import com.stockify.catalog.shared.exception.InvalidValueException;
import com.stockify.catalog.shared.rule.UserRule;
import org.jmolecules.ddd.types.ValueObject;
import org.jspecify.annotations.NonNull;

public record UserId(@NonNull Long value) implements ValueObject {

    public UserId {
        if (value < UserRule.UserId.MIN_LENGTH) throw new InvalidValueException(UserRule.UserId.POSITIVE_MSG);
    }

    public static @NonNull UserId of(@NonNull Long value) {
        return new UserId(value);
    }
}
