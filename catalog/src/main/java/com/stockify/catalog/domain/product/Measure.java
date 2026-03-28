package com.stockify.catalog.domain.product;

import com.stockify.catalog.domain.product.rule.MeasureRule;
import com.stockify.catalog.shared.exception.InvalidValueException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Identifier;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

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
            @Nullable MeasureId id,
            @NonNull String unit,
            @NonNull Integer precision,
            @NonNull Integer scale
    ) {
        if (unit.isBlank())
            throw new InvalidValueException(MeasureRule.Unit.BLANK_MSG);
        if (unit.length() > MeasureRule.Unit.MAX_LENGTH)
            throw new InvalidValueException(MeasureRule.Unit.MAX_LENGTH_MSG, unit.length());

        if (precision < MeasureRule.Precision.MIN_VALUE)
            throw new InvalidValueException(MeasureRule.Precision.MIN_VALUE_MSG);
        if (precision > MeasureRule.Precision.MAX_VALUE)
            throw new InvalidValueException(MeasureRule.Precision.MAX_VALUE_MSG);

        if (scale < MeasureRule.Scale.MIN_VALUE)
            throw new InvalidValueException(MeasureRule.Scale.MIN_VALUE_MSG);
        if (scale > MeasureRule.Scale.MAX_VALUE)
            throw new InvalidValueException(MeasureRule.Scale.MAX_VALUE_MSG);

        return new Measure(id, unit, precision, scale);
    }
}
