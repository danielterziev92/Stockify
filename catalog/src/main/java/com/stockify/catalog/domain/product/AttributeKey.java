package com.stockify.catalog.domain.product;

import com.stockify.catalog.domain.product.rule.AttributeRule;
import com.stockify.catalog.shared.exception.InvalidValueException;
import jakarta.annotation.Nonnull;
import lombok.Getter;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Identifier;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

@Getter
public class AttributeKey implements AggregateRoot<AttributeKey, AttributeKey.AttributeKeyId> {

    public record AttributeKeyId(Long value) implements Identifier {
    }

    private final AttributeKeyId id;
    private final String name;
    private final Set<AttributeValue> values;

    private AttributeKey(AttributeKeyId id, String name) {
        this.id = id;
        this.name = name;
        this.values = new HashSet<>();
    }

    public static @NonNull AttributeKey create(@Nullable AttributeKeyId id, @Nonnull String name) {
        if (name.isBlank())
            throw new InvalidValueException(AttributeRule.AttributeKey.Name.BLANK_MSG);

        if (name.length() > AttributeRule.AttributeKey.Name.MAX_LENGTH)
            throw new InvalidValueException(AttributeRule.AttributeKey.Name.MAX_LENGTH_MSG, name.length());

        return new AttributeKey(id, name);
    }

    public void addValue(@NonNull AttributeValue value) {
        if (this.values.contains(value))
            throw new InvalidValueException(AttributeRule.AttributeValue.Value.DUPLICATE_MSG, value.getValue());

        this.values.add(value);
    }
}
