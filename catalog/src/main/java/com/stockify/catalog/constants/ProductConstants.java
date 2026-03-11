package com.stockify.catalog.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProductConstants {

    // ProductType
    public static final int TYPE_NAME_MAX_LENGTH = 20;

    // Product
    public static final int NAME_MAX_LENGTH = 100;
    public static final int ABBREVIATION_MAX_LENGTH = 20;
    public static final int BASE_PRICE_PRECISION = 19;
    public static final int BASE_PRICE_SCALE = 8;

    // ProductImage
    public static final int URL_MAX_LENGTH = 200;
}
