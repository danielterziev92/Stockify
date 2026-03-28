package com.stockify.catalog.domain.product.rule;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TaxRule {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Name {
        public static final int MAX_LENGTH = 20;

        public static final String BLANK_MSG = "tax.name.blank";
        public static final String MAX_LENGTH_MSG = "tax.name.max-length";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Rate {
        public static final BigDecimal MIN_VALUE = BigDecimal.ZERO;
        public static final BigDecimal MAX_VALUE = BigDecimal.valueOf(100);

        public static final String MIN_VALUE_MSG = "tax.value.min-value";
        public static final String MAX_VALUE_MSG = "tax.value.max-value";
        public static final String INVALID_VALUE_MSG = "tax.value.invalid-value";
    }

}
