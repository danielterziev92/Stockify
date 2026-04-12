package com.stockify.identity.domain.profile;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Validation constants for {@link Profile} aggregate fields.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProfileRule {

    /**
     * Constraints for {@link PersonName} value objects (first name, last name).
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class PersonName {
        public static final int MIN_LENGTH = 2;
        public static final int MAX_LENGTH = 50;

        public static final String BLANK_MSG = "user.profile.name.blank";
        public static final String MIN_LENGTH_MSG = "user.profile.name.min-length";
        public static final String MAX_LENGTH_MSG = "user.profile.name.max-length";
    }
}
