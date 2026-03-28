package com.stockify.catalog.domain.product;

import com.stockify.catalog.domain.product.rule.BarcodeRule;
import com.stockify.catalog.domain.product.specification.BarcodeSpecificationRegistry;
import com.stockify.catalog.shared.exception.InvalidValueException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.jmolecules.ddd.types.Entity;
import org.jmolecules.ddd.types.Identifier;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Barcode implements Entity<Product, Barcode.BarcodeId> {

    public record BarcodeId(Long value) implements Identifier {
    }

    private final BarcodeId id;
    private final String value;
    private final BarcodeType type;

    public static @NonNull Barcode create(
            @NonNull String value,
            @NonNull BarcodeType type
    ) {
        validateValue(value, type);

        return new Barcode(null, value, type);
    }

    public static @NonNull Barcode reconstitute(
            @NonNull BarcodeId id,
            @NonNull String value,
            @NonNull BarcodeType type
    ) {
        return new Barcode(id, value, type);
    }

    public static void validateValue(@NonNull String value, @NonNull BarcodeType type) {
        boolean valid = BarcodeSpecificationRegistry
                .get(type)
                .isSatisfiedBy(value);

        if (!valid) throw new InvalidValueException(BarcodeRule.Value.INVALID_MSG);
    }
}
