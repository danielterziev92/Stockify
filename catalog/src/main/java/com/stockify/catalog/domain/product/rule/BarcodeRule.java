package com.stockify.catalog.domain.product.rule;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BarcodeRule {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Value {
        public static final String NO_SPECIFICATION_MSG = "barcode.value.no-specification";
        public static final String INVALID_MSG = "barcode.value.invalid";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Internal {
        public static final String PREFIX = "20";

        public static final int PREFIX_LENGTH = PREFIX.length();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Ean8 {
        public static final int LENGTH = 8;

        public static final String NO_SPECIFICATION_MSG = "barcode.value.no-specification";
        public static final String INVALID_MSG = "barcode.value.invalid";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Ean13 {
        public static final int LENGTH = 13;


    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Upc {
        public static final int LENGTH = 12;
    }
}
