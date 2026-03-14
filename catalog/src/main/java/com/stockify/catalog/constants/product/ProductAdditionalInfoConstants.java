package com.stockify.catalog.constants.product;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProductAdditionalInfoConstants {

    public static final int WIDTH_PRECISION = 19;
    public static final int WIDTH_SCALE = 2;
    public static final String WEIGHT_MIN_VALUE_MESSAGE = "Weight cannot be less than {value}";

    public static final int HEIGHT_PRECISION = 19;
    public static final int HEIGHT_SCALE = 2;
    public static final String HEIGHT_MIN_VALUE_MESSAGE = "Height cannot be less than {value}";

    public static final int DEPTH_PRECISION = 19;
    public static final int DEPTH_SCALE = 2;
    public static final String DEPTH_MIN_VALUE_MESSAGE = "Depth cannot be less than {value}";

    public static final int DIMENSION_TYPE_MAX_LENGTH = 20;
    public static final String DIMENSION_TYPE_SIZE_MESSAGE = "Dimension type cannot exceed {max} characters";

    public static final String PRODUCT_ID_NOT_NULL_MESSAGE = "Product id cannot be null";
}
