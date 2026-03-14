package com.stockify.catalog.constants.product;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProductBarcodeConstants {
    public static final int VALUE_MAX_LENGTH = 150;
    public static final String VALUE_NOT_BLANK_MESSAGE = "Product barcode cannot be blank";
    public static final String VALUE_SIZE_MESSAGE = "Product barcode cannot exceed {max} characters";

    public static final int TYPE_MAX_LENGTH = 10;
    public static final String TYPE_NOT_BLANK_MESSAGE = "Product barcode type cannot be blank";
    public static final String TYPE_SIZE_MESSAGE = "Product barcode type cannot exceed {max} characters";

    public static final String VARIANT_ID_NOT_NULL_MESSAGE = "Product variant id cannot be null";
}
