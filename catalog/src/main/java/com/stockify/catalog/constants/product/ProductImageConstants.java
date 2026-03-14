package com.stockify.catalog.constants.product;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProductImageConstants {

    public static final int URL_MAX_LENGTH = 200;

    public static final String URL_NOT_FOUND_MESSAGE = "Product image not found by id: %d";
    public static final String URL_NOT_BLANK_MESSAGE = "Product image url cannot be blank";
    public static final String URL_SIZE_MESSAGE = "Product image url cannot exceed {max} characters";

    public static final int DISPLAY_ORDER_MIN_VALUE = 0;
    public static final String DISPLAY_ORDER_NOT_NULL_MESSAGE = "Product image display order cannot be null";
    public static final String DISPLAY_ORDER_MIN_VALUE_MESSAGE = "Product image display order cannot be less than {value}";

    public static final String IS_PRIMARY_NOT_NULL_MESSAGE = "Product image is primary cannot be null";

    public static final String PRODUCT_ID_NOT_NULL_MESSAGE = "Product id cannot be null";
}
