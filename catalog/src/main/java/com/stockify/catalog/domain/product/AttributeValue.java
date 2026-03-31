package com.stockify.catalog.domain.product;

import com.stockify.catalog.domain.product.rule.AttributeRule;
import com.stockify.catalog.domain.product.vo.AttributeKeyId;
import com.stockify.catalog.domain.product.vo.AttributeValueId;
import com.stockify.catalog.shared.exception.InvalidValueException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.jmolecules.ddd.types.Entity;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * Entity representing a single possible value for an {@link AttributeKey}
 * (e.g. "Red" for key "Color", or "XL" for key "Size").
 *
 * <p>An {@link AttributeValue} is always owned by an {@link AttributeKey} and must not
 * be mutated directly — all changes must go through the aggregate root.
 */
@Getter
@FieldNameConstants
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AttributeValue implements Entity<AttributeKey, AttributeValueId> {

    private final AttributeValueId id;
    private final AttributeKeyId keyId;
    private final String value;
    private final String abbreviation;


    public static @NonNull AttributeValue create(
            @NonNull AttributeKeyId keyId,
            @NonNull String value,
            @Nullable String abbreviation
    ) {
        validateValue(value);
        validateAbbreviation(abbreviation);
        return new AttributeValue(AttributeValueId.generate(), keyId, value, abbreviation);
    }

    public static @NonNull AttributeValue reconstitute(
            @NonNull AttributeValueId id,
            @NonNull AttributeKeyId keyId,
            @NonNull String value,
            @Nullable String abbreviation
    ) {
        return new AttributeValue(id, keyId, value, abbreviation);
    }

    private static void validateValue(@NonNull String value) {
        if (value.isBlank())
            throw new InvalidValueException(AttributeRule.AttributeValue.Value.BLANK_MSG);

        if (value.length() > AttributeRule.AttributeValue.Value.MAX_LENGTH)
            throw new InvalidValueException(AttributeRule.AttributeValue.Value.MAX_LENGTH_MSG, value.length());
    }

    private static void validateAbbreviation(@Nullable String abbreviation) {
        if (abbreviation == null) return;

        if (abbreviation.isBlank())
            throw new InvalidValueException(AttributeRule.AttributeValue.Abbreviation.BLANK_MSG);

        if (abbreviation.length() > AttributeRule.AttributeValue.Abbreviation.MAX_LENGTH)
            throw new InvalidValueException(AttributeRule.AttributeValue.Abbreviation.MAX_LENGTH_MSG, abbreviation.length());
    }
}