package com.stockify.catalog.domain.product.vo;

import com.stockify.catalog.domain.product.rule.AttributeRule;
import com.stockify.catalog.shared.exception.InvalidValueException;
import org.jmolecules.ddd.types.ValueObject;
import org.jspecify.annotations.NonNull;

public record AttributeKeyName(@NonNull String value) implements ValueObject {
    public AttributeKeyName {
        if (value.isBlank()) throw new InvalidValueException(AttributeRule.AttributeKey.Name.BLANK_MSG);

        if (value.length() > AttributeRule.AttributeKey.Name.MAX_LENGTH)
            throw new InvalidValueException(AttributeRule.AttributeKey.Name.MAX_LENGTH_MSG, value.length());
    }
}
