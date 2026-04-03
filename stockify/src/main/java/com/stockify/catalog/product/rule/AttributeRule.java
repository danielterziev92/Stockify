package com.stockify.catalog.product.rule;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AttributeRule {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class AttributeKey {

        @NoArgsConstructor(access = AccessLevel.PRIVATE)
        public static final class Generic {
            public static final String NOT_FOUND_MSG = "attribute.key.generic.not-found";
        }

        @NoArgsConstructor(access = AccessLevel.PRIVATE)
        public static final class Name {
            public static final int MAX_LENGTH = 50;

            public static final String BLANK_MSG = "attribute.key.name.blank";
            public static final String MAX_LENGTH_MSG = "attribute.key.name.too-long";
            public static final String DUPLICATE_MSG = "attribute.key.name.duplicate";
        }
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class AttributeValue {

        @NoArgsConstructor(access = AccessLevel.PRIVATE)
        public static final class Generic {
            public static final String ALREADY_EXISTS_MSG = "attribute.value.generic.already-exists";
            public static final String NOT_FOUND_MSG = "attribute.value.generic.not-found";
        }

        @NoArgsConstructor(access = AccessLevel.PRIVATE)
        public static final class Value {
            public static final int MAX_LENGTH = 100;

            public static final String BLANK_MSG = "attribute.value.value.blank";
            public static final String MAX_LENGTH_MSG = "attribute.value.value.too-long";
            public static final String DUPLICATE_MSG = "attribute.value.value.duplicate";
        }

        @NoArgsConstructor(access = AccessLevel.PRIVATE)
        public static final class Abbreviation {
            public static final int MAX_LENGTH = 10;

            public static final String BLANK_MSG = "attribute.value.abbreviation.blank";
            public static final String MAX_LENGTH_MSG = "attribute.value.abbreviation.too-long";
            public static final String DUPLICATE_MSG = "attribute.value.abbreviation.duplicate";
        }
    }
}
