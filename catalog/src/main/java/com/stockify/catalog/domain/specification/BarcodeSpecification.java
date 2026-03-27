package com.stockify.catalog.domain.specification;

import com.stockify.catalog.domain.Barcode;
import com.stockify.catalog.domain.BarcodeType;
import com.stockify.catalog.domain.rule.BarcodeRule;
import org.jspecify.annotations.NonNull;

import java.util.random.RandomGenerator;

public abstract class BarcodeSpecification implements Specification<String> {

    public abstract @NonNull BarcodeType supportedType();

    protected abstract int length();

    protected @NonNull Barcode generate() {
        StringBuilder value = new StringBuilder(BarcodeRule.Internal.PREFIX);
        int remaining = length() - BarcodeRule.Internal.PREFIX_LENGTH;

        RandomGenerator rng = RandomGenerator.getDefault();
        for (int i = 0; i < remaining; i++) value.append(rng.nextInt(10));

        return Barcode.of(null, value.toString(), supportedType());
    }

    protected boolean isDigitalOnly(@NonNull String value) {
        return value.matches("^[0-9]+$");
    }

    protected boolean hasLength(@NonNull String value, int expected) {
        return value.length() == expected;
    }
}
