package com.stockify.catalog.product;

import com.stockify.catalog.exception.InvalidValueException;
import com.stockify.catalog.product.rule.AttributeRule;
import com.stockify.catalog.product.vo.AttributeValueId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.jmolecules.ddd.types.Entity;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Getter
@FieldNameConstants
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AttributeValue implements Entity<AttributeKey, AttributeValueId> {

    private final AttributeValueId id;
    private final String value;
    private final String abbreviation;


    public static @NonNull AttributeValue create(
            @NonNull String value,
            @Nullable String abbreviation
    ) {
        validateValue(value);
        validateAbbreviation(abbreviation);
        return new AttributeValue(AttributeValueId.generate(), value, abbreviation);
    }

    public static @NonNull AttributeValue reconstitute(
            @NonNull AttributeValueId id,
            @NonNull String value,
            @Nullable String abbreviation
    ) {
        return new AttributeValue(id, value, abbreviation);
    }

    public @NonNull AttributeValue changeValue(@NonNull String value) {
        validateValue(value);
        return new AttributeValue(this.id, value, this.abbreviation);
    }

    public @NonNull AttributeValue withAbbreviation(@Nullable String abbreviation) {
        validateAbbreviation(abbreviation);
        return new AttributeValue(this.id, this.value, abbreviation);
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
