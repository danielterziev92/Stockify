package com.stockify.catalog.domain.product;

import com.stockify.catalog.domain.product.rule.MeasureRule;
import com.stockify.catalog.shared.exception.InvalidValueException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Identifier;
import org.jspecify.annotations.NonNull;

@Getter
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class Measure implements AggregateRoot<Measure, Measure.MeasureId> {

    public record MeasureId(Long value) implements Identifier {
    }

    private final MeasureId id;
    private String unit;
    private Integer precision;
    private Integer scale;

    public static @NonNull Measure create(
            @NonNull String unit,
            @NonNull Integer precision,
            @NonNull Integer scale
    ) {
        validateUnit(unit);
        validatePrecision(precision);
        validateScale(scale);

        return new Measure(null, unit, precision, scale);
    }

    public static @NonNull Measure reconstitute(
            @NonNull MeasureId id,
            @NonNull String unit,
            @NonNull Integer precision,
            @NonNull Integer scale
    ) {
        return new Measure(id, unit, precision, scale);
    }

    private static void validateUnit(@NonNull String unit) {
        if (unit.isBlank()) throw new InvalidValueException(MeasureRule.Unit.BLANK_MSG);

        if (unit.length() > MeasureRule.Unit.MAX_LENGTH)
            throw new InvalidValueException(MeasureRule.Unit.MAX_LENGTH_MSG, unit.length());
    }

    private static void validatePrecision(@NonNull Integer precision) {
        if (precision < MeasureRule.Precision.MIN_VALUE)
            throw new InvalidValueException(MeasureRule.Precision.MIN_VALUE_MSG);

        if (precision > MeasureRule.Precision.MAX_VALUE)
            throw new InvalidValueException(MeasureRule.Precision.MAX_VALUE_MSG);
    }

    private static void validateScale(@NonNull Integer scale) {
        if (scale < MeasureRule.Scale.MIN_VALUE)
            throw new InvalidValueException(MeasureRule.Scale.MIN_VALUE_MSG);

        if (scale > MeasureRule.Scale.MAX_VALUE)
            throw new InvalidValueException(MeasureRule.Scale.MAX_VALUE_MSG);
    }
}
