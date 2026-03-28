package com.stockify.catalog.domain.product;

import com.stockify.catalog.domain.product.rule.TaxRule;
import com.stockify.catalog.domain.product.specification.TaxRateSpecification;
import com.stockify.catalog.shared.exception.InvalidValueException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Identifier;
import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Tax implements AggregateRoot<Tax, Tax.TaxId> {

    public record TaxId(Long value) implements Identifier {
    }

    private final TaxId id;
    private final String name;
    private final BigDecimal value;

    public static @NonNull Tax create(
            @NonNull String name,
            @NonNull BigDecimal value,
            @NonNull TaxRateSpecification specification
    ) {
        validateName(name);
        validateValue(value);

        if (!specification.isSatisfiedBy(value))
            throw new InvalidValueException(TaxRule.Value.INVALID_VALUE_MSG, value);

        return new Tax(null, name, value);
    }

    public static @NonNull Tax reconstitute(@NonNull TaxId id, @NonNull String name, @NonNull BigDecimal value) {
        return new Tax(id, name, value);
    }

    private static void validateName(@NonNull String name) {
        if (name.isBlank())
            throw new InvalidValueException(TaxRule.Name.BLANK_MSG);

        if (name.length() > TaxRule.Name.MAX_LENGTH)
            throw new InvalidValueException(TaxRule.Name.MAX_LENGTH_MSG, name.length());
    }

    private static void validateValue(@NonNull BigDecimal value) {
        if (value.compareTo(TaxRule.Value.MIN_VALUE) < 0)
            throw new InvalidValueException(TaxRule.Value.MIN_VALUE_MSG);

        if (value.compareTo(TaxRule.Value.MAX_VALUE) > 0)
            throw new InvalidValueException(TaxRule.Value.MAX_VALUE_MSG);
    }
}
