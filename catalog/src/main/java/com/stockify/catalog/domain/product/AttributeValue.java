package com.stockify.catalog.domain.product;

import com.stockify.catalog.domain.product.rule.AttributeRule;
import com.stockify.catalog.shared.exception.InvalidValueException;
import jakarta.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jmolecules.ddd.types.Entity;
import org.jmolecules.ddd.types.Identifier;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AttributeValue implements Entity<AttributeKey, AttributeValue.AttributeValueId> {

    public record AttributeValueId(Long value) implements Identifier {
    }

    private final AttributeValueId id;
    private final AttributeKey.AttributeKeyId keyId;
    private final String value;
    private final String abbreviation;

    public static @Nonnull AttributeValue create(
            @Nullable AttributeValueId id,
            AttributeKey.@NonNull AttributeKeyId keyId,
            @NonNull String value,
            @Nullable String abbreviation
    ) {
        if (value.isBlank())
            throw new InvalidValueException(AttributeRule.AttributeValue.Value.BLANK_MSG);

        if (value.length() > AttributeRule.AttributeValue.Value.MAX_LENGTH)
            throw new InvalidValueException(AttributeRule.AttributeValue.Value.MAX_LENGTH_MSG, value.length());

        if (abbreviation != null) {
            if (abbreviation.isBlank())
                throw new InvalidValueException(AttributeRule.AttributeValue.Abbreviation.BLANK_MSG);

            if (abbreviation.length() > AttributeRule.AttributeValue.Abbreviation.MAX_LENGTH)
                throw new InvalidValueException(AttributeRule.AttributeValue.Abbreviation.MAX_LENGTH_MSG, abbreviation.length());
        }

        return new AttributeValue(id, keyId, value, abbreviation);
    }
}
