package com.stockify.catalog.shared.rule;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserRule {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class UserId {
        public static final int MIN_LENGTH = 1;

        public static final String POSITIVE_MSG = "shared.user.id.positive";
    }
}
