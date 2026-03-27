package com.stockify.catalog.domain.specification;

import com.stockify.catalog.domain.BarcodeType;
import com.stockify.catalog.domain.rule.BarcodeRule;
import org.jspecify.annotations.NonNull;

public class EAN8Specification extends BarcodeSpecification {

    @Override
    public @NonNull BarcodeType supportedType() {
        return BarcodeType.EAN8;
    }

    @Override
    protected int length() {
        return BarcodeRule.Ean8.LENGTH;
    }

    @Override
    public boolean isSatisfiedBy(String candidate) {
        return isDigitalOnly(candidate) && hasLength(candidate, BarcodeRule.Ean8.LENGTH);
    }
}
