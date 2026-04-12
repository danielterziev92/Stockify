package com.stockify.identity.domain.user.validation.phone.country;

import com.stockify.identity.domain.user.validation.phone.CountryRule;
import org.jspecify.annotations.NonNull;

import java.util.regex.Pattern;

/**
 * {@link CountryRule} implementation for German phone numbers.
 * <p>
 * Validates numbers that begin with the {@code +49} country code, followed by
 * a non-zero leading digit and 6–12 additional digits, covering the full range
 * of German mobile and landline formats.
 */
public enum GermanRule implements CountryRule {
    INSTANCE;

    private static final Pattern PATTERN = Pattern.compile("^\\+49[1-9][0-9]{6,12}$");

    /**
     * {@inheritDoc}
     *
     * @return {@code "+49"}
     */
    @Override
    public @NonNull String countryCode() {
        return "+49";
    }

    /**
     * {@inheritDoc}
     *
     * <p>Accepted format: {@code +49} followed by a non-zero digit and 6–12 digits
     * (e.g. {@code +4915123456789}).
     */
    @Override
    public @NonNull Pattern pattern() {
        return PATTERN;
    }
}
