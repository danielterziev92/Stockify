package com.stockify.catalog.domain.product.rule;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class AttributeRule {

    @NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
    public static final class AttributeKey {

        @NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
        public static final class Name {
            public static final int MAX_LENGTH = 50;

            public static final String BLANK_MSG = "attribute.key.name.blank";
            public static final String MAX_LENGTH_MSG = "attribute.key.name.too-long";
        }
    }

    @NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
    public static final class AttributeValue {

        @NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
        public static final class Value {
            public static final int MAX_LENGTH = 100;

            public static final String BLANK_MSG = "attribute.value.value.blank";
            public static final String MAX_LENGTH_MSG = "attribute.value.value.too-long";
            public static final String DUPLICATE_MSG = "attribute.value.value.duplicate";
        }

        @NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
        public static final class Abbreviation {
            public static final int MAX_LENGTH = 10;

            public static final String BLANK_MSG = "attribute.value.abbreviation.blank";
            public static final String MAX_LENGTH_MSG = "attribute.value.abbreviation.too-long";
        }
    }

    @NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
    public static final class VariantAttribute {
        public static final String NULL_VALUE_MSG = "attribute.variant.value.null";
        public static final String INVALID_ORDER_MSG = "attribute.variant.order.invalid";
        public static final String DUPLICATE_KEY_MSG = "attribute.variant.key.duplicate";
    }
}
