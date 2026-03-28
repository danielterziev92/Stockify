package com.stockify.catalog.domain.product.rule;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CurrencyRule {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Code {
        public static final int LENGTH = 3;

        public static final String BLANK_CODE_MSG = "currency.code.blank";
        public static final String LENGTH_MSG = "currency.code.length";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Name {
        public static final int MAX_LENGTH = 20;

        public static final String BLANK_NAME_MSG = "currency.name.blank";
        public static final String MAX_LENGTH_MSG = "currency.name.max-length";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Value {
        public static final int SCALE = 6;
        public static final BigDecimal MAX_VALUE = new BigDecimal("99999.999999");

        public static final String MIN_VALUE_MSG = "currency.value.min-value";
        public static final String MAX_VALUE_MSG = "currency.value.max-value";
    }
}
