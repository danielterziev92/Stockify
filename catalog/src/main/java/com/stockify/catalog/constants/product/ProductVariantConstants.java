package com.stockify.catalog.constants.product;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductVariantConstants {

    // Name
    public static final int NAME_MAX_LENGTH = 100;

    // Sku
    public static final int SKU_MAX_LENGTH = 20;

    // AdditionalPrice
    public static final double ADDITIONAL_PRICE_DEFAULT_VALUE = 0.0;
    public static final int ADDITIONAL_PRICE_PRECISION = 19;
    public static final int ADDITIONAL_PRICE_SCALE = 8;

    // Active
    public static final boolean ACTIVE_DEFAULT_VALUE = true;
}
