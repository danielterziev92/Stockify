package com.stockify.user.domain.rule;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class VerificationCodeRule {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Code {
        public static final int LENGTH = 8;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class ExpireAt {
        public static final int DURATION_IN_SECONDS = 3600;

        public static final String EXPIRE_ALREADY_MSG = "verification.code.expire.already";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class RedirectUrl {
        public static final String AFTER_VERIFICATION = "/verify";
        public static final String AFTER_SUCCESS_VERIFICATION = "/company/detail";
    }
}
