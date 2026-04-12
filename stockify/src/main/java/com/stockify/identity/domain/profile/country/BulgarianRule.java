package com.stockify.identity.domain.profile.country;

import com.stockify.identity.domain.profile.CountryRule;
import org.jspecify.annotations.NonNull;

import java.util.regex.Pattern;

/**
 * {@link CountryRule} implementation for Bulgarian phone numbers.
 * <p>
 * Validates numbers that begin with the {@code +359} country code followed by
 * exactly 9 digits, matching the standard Bulgarian mobile and landline format.
 */
public enum BulgarianRule implements CountryRule {
    INSTANCE;

    private static final Pattern PATTERN = Pattern.compile("^\\+359[0-9]{9}$");

    /**
     * {@inheritDoc}
     *
     * @return {@code "+359"}
     */
    @Override
    public @NonNull String countryCode() {
        return "+359";
    }

    /**
     * {@inheritDoc}
     *
     * <p>Accepted format: {@code +359} followed by exactly 9 digits
     * (e.g. {@code +359881234567}).
     */
    @Override
    public @NonNull Pattern pattern() {
        return PATTERN;
    }
}
