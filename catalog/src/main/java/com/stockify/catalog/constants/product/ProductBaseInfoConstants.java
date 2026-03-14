package com.stockify.catalog.constants.product;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProductBaseInfoConstants {

    public static final int NAME_MAX_LENGTH = 100;
    public static final int ABBREVIATION_MAX_LENGTH = 20;
    public static final int DESCRIPTION_MAX_LENGTH = 150;
    public static final int BASE_PRICE_PRECISION = 19;
    public static final int BASE_PRICE_SCALE = 8;

}
