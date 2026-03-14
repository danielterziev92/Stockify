package com.stockify.catalog.constants.product;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductVariantConstants {

    public static final int SKU_MAX_LENGTH = 20;
    public static final String SKU_SIZE_MESSAGE = "Product variant sku cannot exceed {max} characters";

    public static final double ADDITIONAL_PRICE_DEFAULT_VALUE = 0.0;
    public static final int ADDITIONAL_PRICE_PRECISION = 19;
    public static final int ADDITIONAL_PRICE_SCALE = 8;
    public static final String ADDITIONAL_PRICE_NOT_NULL_MESSAGE = "Product variant additional price cannot be null";
    public static final String ADDITIONAL_PRICE_MIN_VALUE_MESSAGE = "Product variant additional price cannot be less than {value}";

    public static final boolean ACTIVE_DEFAULT_VALUE = true;
}
