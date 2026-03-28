package com.stockify.catalog.domain.product.specification;

import com.stockify.catalog.domain.product.BarcodeType;
import com.stockify.catalog.domain.product.rule.BarcodeRule;
import com.stockify.catalog.shared.exception.InvalidValueException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BarcodeSpecificationRegistry {

    private static final Map<BarcodeType, BarcodeSpecification> REGISTRY = Map.of(
            BarcodeType.EAN8, new EAN8Specification(),
            BarcodeType.EAN13, new EAN13Specification(),
            BarcodeType.UPC, new UPCSpecification()
    );

    public static @NonNull BarcodeSpecification get(@NonNull BarcodeType type) {
        BarcodeSpecification specification = REGISTRY.get(type);
        if (specification == null)
            throw new InvalidValueException(BarcodeRule.Value.NO_SPECIFICATION_MSG);

        return specification;
    }
}
