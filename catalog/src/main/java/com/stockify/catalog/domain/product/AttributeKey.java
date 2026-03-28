package com.stockify.catalog.domain.product;

import com.stockify.catalog.domain.product.rule.AttributeRule;
import com.stockify.catalog.shared.exception.EntityNotFoundException;
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
    private String name;
    private final Set<AttributeValue> values;

    private AttributeKey(AttributeKeyId id, String name) {
        this.id = id;
        this.name = name;
        this.values = new HashSet<>();
    }

    public static @NonNull AttributeKey create(@Nonnull String name) {
        validateName(name);

        return new AttributeKey(null, name);
    }

    public static @NonNull AttributeKey reconstitute(
            @NonNull AttributeKeyId id,
            @NonNull String name,
            @NonNull Set<AttributeValue> values
    ) {
        AttributeKey attributeKey = new AttributeKey(id, name);
        attributeKey.values.addAll(values);
        return attributeKey;
    }

    public void changeName(@NonNull String name) {
        validateName(name);
        this.name = name;
    }

    public void addValue(@NonNull AttributeValue value) {
        boolean duplicate = this.values.stream()
                .anyMatch(v -> v.getValue().equalsIgnoreCase(value.getValue()));

        if (duplicate)
            throw new InvalidValueException(AttributeRule.AttributeValue.Value.DUPLICATE_MSG, value.getValue());

        this.values.add(value);
    }

    public void updateValue(
            @NonNull AttributeValue existingValue,
            @NonNull String newValue,
            @Nullable String newAbbreviation
    ) {
        boolean duplicate = this.values.stream()
                .filter(v -> !v.getId().equals(existingValue.getId()))
                .anyMatch(v -> v.getValue().equalsIgnoreCase(newValue));

        if (duplicate)
            throw new InvalidValueException(AttributeRule.AttributeValue.Value.DUPLICATE_MSG, newValue);

        this.values.remove(existingValue);
        this.values.add(AttributeValue.create(existingValue.getKeyId(), newValue, newAbbreviation));
    }

    public void removeValue(@NonNull AttributeValue value) {
        if (!this.values.contains(value))
            throw new EntityNotFoundException(
                    AttributeRule.Generic.NOT_FOUND_MSG,
                    AttributeValue.class.getSimpleName(),
                    value.getId().value()
            );

        this.values.remove(value);
    }

    private static void validateName(@NonNull String name) {
        if (name.isBlank()) throw new InvalidValueException(AttributeRule.AttributeKey.Name.BLANK_MSG);

        if (name.length() > AttributeRule.AttributeKey.Name.MAX_LENGTH)
            throw new InvalidValueException(AttributeRule.AttributeKey.Name.MAX_LENGTH_MSG, name.length());
    }
}
