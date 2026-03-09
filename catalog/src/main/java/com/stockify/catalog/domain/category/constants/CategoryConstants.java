package com.stockify.catalog.domain.category.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CategoryConstants {
    public static final String CATEGORY_NOT_FOUND_BY_ID_MESSAGE = "Category not found by id: %d";
    public static final String CATEGORY_OWN_PARENT_MESSAGE = "Category cannot be its own parent.";
    public static final String CATEGORY_CIRCULAR_REFERENCE_MESSAGE = "Category cannot be a descendant of itself.";

    public static final int NAME_MAX_LENGTH = 100;
    public static final String NAME_NOT_BLANK_MESSAGE = "Category name cannot be blank";
    public static final String NAME_SIZE_MESSAGE = "Category name cannot exceed {max} characters";

    public static final boolean ACTIVE_DEFAULT_VALUE = true;

    public static final int DISPLAY_ORDER_DEFAULT_VALUE = 0;
    public static final int DISPLAY_ORDER_MIN_VALUE = 0;
    public static final String DISPLAY_ORDER_MIN_VALUE_MESSAGE = "Display order cannot be less than {value}";
    public static final String DISPLAY_ORDER_NEGATIVE_MESSAGE = "Display order cannot be negative";

    public static final int PARENT_ID_MIN_VALUE = 1;
    public static final String PARENT_ID_MIN_VALUE_MESSAGE = "Parent id cannot be less than {value}";
}
