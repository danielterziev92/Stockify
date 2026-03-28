package com.stockify.catalog.domain.product.specification;

import com.stockify.catalog.domain.product.BarcodeType;
import com.stockify.catalog.domain.product.rule.BarcodeRule;
import org.jspecify.annotations.NonNull;

public class EAN13Specification extends BarcodeSpecification {

    @Override
    public @NonNull BarcodeType supportedType() {
        return BarcodeType.EAN13;
    }

    @Override
    protected int length() {
        return BarcodeRule.Ean13.LENGTH;
    }

    @Override
    public boolean isSatisfiedBy(@NonNull String candidate) {
        return isDigitalOnly(candidate) && hasLength(candidate, BarcodeRule.Ean13.LENGTH);
    }
}
