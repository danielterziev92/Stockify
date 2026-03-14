package com.stockify.catalog.constants.product;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProductAttributeKeyConstants {

    public static final int NAME_MAX_LENGTH = 50;

    public static final String NOT_FOUND_BY_ID_MESSAGE = "Product attribute key not found by id: %d";
    public static final String NOT_FOUND_BY_NAME_MESSAGE = "Product attribute key not found by name: %s";
    public static final String ALREADY_EXISTS_MESSAGE = "Product attribute key already exists: %s";
    public static final String NAME_NOT_BLANK_MESSAGE = "Product attribute key name cannot be blank";
    public static final String NAME_SIZE_MESSAGE = "Product attribute key name cannot exceed {max} characters";
}
