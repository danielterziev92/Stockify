package com.stockify.catalog.constants.product;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProductVariantAttributeConstants {
    public static final String VARIANT_ID_NOT_NULL_MESSAGE = "Variant id cannot be null";
    public static final String ATTRIBUTE_VALUE_ID_NOT_NULL_MESSAGE = "Attribute value id cannot be null";
    public static final String ORDER_NOT_NULL_MESSAGE = "Order cannot be null";
    public static final String ORDER_MIN_VALUE_MESSAGE = "Order cannot be less than {value}";
}
