package com.stockify.catalog.constants.company;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CompanyConstants {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Name {
        public static final int MAX_LENGTH = 100;

        public static final String BLANK_MESSAGE = "company.name.blank";
        public static final String SIZE_MESSAGE = "company.name.size";
        public static final String UNIQUE_MESSAGE = "company.name.unique";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class NameEn {
        public static final int MAX_LENGTH = 100;

        public static final String SIZE_MESSAGE = "company.nameEn.size";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Uic {
        /**
         * Valid lengths: 9 digits (legal entity) or 13 digits (branch).
         */
        public static final int MIN_LENGTH = 9;
        public static final int MAX_LENGTH = 13;

        /**
         * Compiled regex pattern — thread-safe, evaluated once at a class load.
         * Matches exactly 9 or exactly 13 digit strings.
         */
        public static final Pattern PATTERN = Pattern.compile("^[0-9]{9,13}$");

        public static final String BLANK_MESSAGE = "company.uic.blank";
        public static final String SIZE_MESSAGE = "company.uic.size";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Vat {
        /**
         * ISO 3166-1 alpha-2 country code.
         */
        public static final String COUNTRY_CODE = "BG";

        /**
         * Minimum total length: "BG" + 9 digits.
         */
        public static final int MIN_LENGTH = 11;
        /**
         * Maximum total length: "BG" + 10 digits.
         */
        public static final int MAX_LENGTH = 12;

        /**
         * Compiled regex pattern — thread-safe, evaluated once at a class load.
         * Matches "BG" followed by 9 or 10 digits (case-sensitive after normalization).
         */
        public static final Pattern PATTERN = Pattern.compile("BG\\d{9,10}");
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Custodian {
        public static final int MAX_LENGTH = 100;

        public static final String SIZE_MESSAGE = "company.custodian.size";
        public static final String BLANK_MESSAGE = "company.custodian.blank";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class CustodianEn {
        public static final int MAX_LENGTH = 100;

        public static final String SIZE_MESSAGE = "company.custodianEn.size";
    }
}
