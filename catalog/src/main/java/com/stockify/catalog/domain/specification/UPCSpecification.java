package com.stockify.catalog.domain.specification;

import com.stockify.catalog.domain.BarcodeType;
import com.stockify.catalog.domain.rule.BarcodeRule;
import org.jspecify.annotations.NonNull;

public class UPCSpecification extends BarcodeSpecification {

    @Override
    public @NonNull BarcodeType supportedType() {
        return BarcodeType.UPC;
    }

    @Override
    protected int length() {
        return BarcodeRule.Upc.LENGTH;
    }

    @Override
    public boolean isSatisfiedBy(String candidate) {
        return isDigitalOnly(candidate) && hasLength(candidate, BarcodeRule.Upc.LENGTH);
    }
}
