package com.stockify.catalog.domain.product.rule;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MeasureRule {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Unit {
        public static final int MAX_LENGTH = 10;

        public static final String BLANK_MSG = "measure.unit.blank";
        public static final String MAX_LENGTH_MSG = "measure.unit.max-length";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Precision {
        public static final int MIN_VALUE = 0;
        public static final int MAX_VALUE = 10;

        public static final String MIN_VALUE_MSG = "measure.precision.min-value";
        public static final String MAX_VALUE_MSG = "measure.precision.max-value";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Scale {
        public static final int MIN_VALUE = 0;
        public static final int MAX_VALUE = 6;

        public static final String MIN_VALUE_MSG = "measure.scale.min-value";
        public static final String MAX_VALUE_MSG = "measure.scale.max-value";
        public static final String EXCEEDS_PRECISION_MSG = "measure.scale.exceeds-precision";
    }
}
