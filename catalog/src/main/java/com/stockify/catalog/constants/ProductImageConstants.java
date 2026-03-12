package com.stockify.catalog.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProductImageConstants {

    public static final int URL_MAX_LENGTH = 200;

    public static final String URL_NOT_FOUND_MESSAGE = "Product image not found by id: %d";
    public static final String URL_NOT_BLANK_MESSAGE = "Product image url cannot be blank";
    public static final String URL_SIZE_MESSAGE = "Product image url cannot exceed {max} characters";
}
