package com.stockify.catalog.constants.product;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProductTypeConstants {
    public static final int NAME_MAX_LENGTH = 20;

    public static final String ALREADY_EXISTS_MESSAGE = "Product type already exists: %s";
    public static final String NOT_FOUND_BY_ID_MESSAGE = "Product type not found by id: %d";
    public static final String NOT_FOUND_BY_NAME_MESSAGE = "Product type not found by name: %s";
    public static final String NAME_NOT_BLANK_MESSAGE = "Product type name cannot be blank";
    public static final String NAME_SIZE_MESSAGE = "Product type name cannot exceed {max} characters";
}
