package com.stockify.catalog.domain.product;

import com.stockify.catalog.domain.product.rule.BarcodeRule;
import com.stockify.catalog.domain.product.specification.BarcodeSpecificationRegistry;
import com.stockify.catalog.shared.exception.InvalidValueException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.jmolecules.ddd.types.Entity;
import org.jmolecules.ddd.types.Identifier;
import org.jspecify.annotations.Nullable;

@Getter
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class Barcode implements Entity<Product, Barcode.BarcodeId> {

    public record BarcodeId(Long value) implements Identifier {
    }

    private final BarcodeId id;
    private String value;
    private BarcodeType type;

    public static @NonNull Barcode of(
            @Nullable BarcodeId id,
            @NonNull String value,
            @NonNull BarcodeType type
    ) {
        boolean valid = BarcodeSpecificationRegistry
                .get(type)
                .isSatisfiedBy(value);

        if (!valid)
            throw new InvalidValueException(BarcodeRule.Value.INVALID_MSG);

        return new Barcode(id, value, type);
    }
}
