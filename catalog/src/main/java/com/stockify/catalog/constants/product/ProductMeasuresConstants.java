package com.stockify.catalog.constants.product;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProductMeasuresConstants {

    public static final int UNIT_MAX_LENGTH = 10;

    public static final String NOT_FOUND_BY_ID_MESSAGE = "Product measure not found by id: %d";
    public static final String ALREADY_EXISTS_MESSAGE = "Product measure already exists: %s";

    public static final String UNIT_NOT_BLANK_MESSAGE = "Unit cannot be blank";
    public static final String UNIT_SIZE_MESSAGE = "Unit cannot exceed {max} characters";
}
