package com.stockify.catalog.constants.product;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProductBaseInfoConstants {

    public static final int NAME_MAX_LENGTH = 100;
    public static final String NAME_NOT_BLANK_MESSAGE = "Product name cannot be blank";
    public static final String NAME_SIZE_MESSAGE = "Product name cannot exceed {max} characters";

    public static final int ABBREVIATION_MAX_LENGTH = 20;
    public static final String ABBREVIATION_SIZE_MESSAGE = "Product abbreviation cannot exceed {max} characters";

    public static final int DESCRIPTION_MAX_LENGTH = 150;
    public static final String DESCRIPTION_SIZE_MESSAGE = "Product description cannot exceed {max} characters";

    public static final int BASE_PRICE_PRECISION = 19;
    public static final int BASE_PRICE_SCALE = 8;
    public static final String BASE_PRICE_NOT_NULL_MESSAGE = "Product base price cannot be null";
    public static final String BASE_PRICE_MIN_VALUE_MESSAGE = "Product base price cannot be less than {value}";

    public static final String TYPE_ID_NOT_NULL_MESSAGE = "Product type id cannot be null";

    public static final String MEASURE_ID_NOT_NULL_MESSAGE = "Product measure id cannot be null";

    public static final String CATEGORY_ID_NOT_NULL_MESSAGE = "Product category id cannot be null";
}
