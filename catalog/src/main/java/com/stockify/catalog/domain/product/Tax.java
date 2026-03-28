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
    private String name;
    private BigDecimal rate;

    public static @NonNull Tax create(
            @NonNull String name,
            @NonNull BigDecimal rate,
            @NonNull TaxRateSpecification specification
    ) {
        validateName(name);
        validateValue(rate, specification);

        return new Tax(null, name, rate);
    }

    public static @NonNull Tax reconstitute(@NonNull TaxId id, @NonNull String name, @NonNull BigDecimal rate) {
        return new Tax(id, name, rate);
    }

    public void changeName(@NonNull String name) {
        validateName(name);
        this.name = name;
    }

    public void changeRate(@NonNull BigDecimal rate, @NonNull TaxRateSpecification specification) {
        validateValue(rate, specification);
        this.rate = rate;
    }

    private static void validateName(@NonNull String name) {
        if (name.isBlank())
            throw new InvalidValueException(TaxRule.Name.BLANK_MSG);

        if (name.length() > TaxRule.Name.MAX_LENGTH)
            throw new InvalidValueException(TaxRule.Name.MAX_LENGTH_MSG, name.length());
    }

    private static void validateValue(@NonNull BigDecimal rate, @NonNull TaxRateSpecification specification) {
        if (rate.compareTo(TaxRule.Rate.MIN_VALUE) < 0)
            throw new InvalidValueException(TaxRule.Rate.MIN_VALUE_MSG);

        if (rate.compareTo(TaxRule.Rate.MAX_VALUE) > 0)
            throw new InvalidValueException(TaxRule.Rate.MAX_VALUE_MSG);

        if (!specification.isSatisfiedBy(rate))
            throw new InvalidValueException(TaxRule.Rate.INVALID_VALUE_MSG, rate);
    }
}
