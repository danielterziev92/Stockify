package com.stockify.catalog.domain.product;

import com.stockify.catalog.domain.product.rule.CurrencyRule;
import com.stockify.catalog.shared.exception.InvalidValueException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Identifier;
import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Currency implements AggregateRoot<Currency, Currency.CurrencyId> {

    public record CurrencyId(UUID value) implements Identifier {
        public static @NonNull CurrencyId generate() {
            return new CurrencyId(UUID.randomUUID());
        }
    }

    private final CurrencyId id;
    private final String code;
    private String name;
    private BigDecimal value;

    public static @NonNull Currency create(
            @NonNull String code,
            @NonNull String name,
            @NonNull BigDecimal value
    ) {
        validateCode(code);
        validateName(name);
        validateValue(value);

        return new Currency(CurrencyId.generate(), code, name, value);
    }

    public static @NonNull Currency reconstitute(
            @NonNull CurrencyId id,
            @NonNull String code,
            @NonNull String name,
            @NonNull BigDecimal value
    ) {
        return new Currency(id, code, name, value);
    }

    private static void validateCode(@NonNull String code) {
        if (code.isBlank())
            throw new InvalidValueException(CurrencyRule.Code.BLANK_CODE_MSG);

        if (code.length() != CurrencyRule.Code.LENGTH)
            throw new InvalidValueException(CurrencyRule.Code.LENGTH_MSG);
    }

    private static void validateName(@NonNull String name) {
        if (name.isBlank())
            throw new InvalidValueException(CurrencyRule.Name.BLANK_NAME_MSG);

        if (name.length() > CurrencyRule.Name.MAX_LENGTH)
            throw new InvalidValueException(CurrencyRule.Name.MAX_LENGTH_MSG, name.length());
    }

    private static void validateValue(@NonNull BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) <= 0)
            throw new InvalidValueException(CurrencyRule.Value.MIN_VALUE_MSG);

        if (value.compareTo(CurrencyRule.Value.MAX_VALUE) > 0)
            throw new InvalidValueException(CurrencyRule.Value.MAX_VALUE_MSG);
    }
}
