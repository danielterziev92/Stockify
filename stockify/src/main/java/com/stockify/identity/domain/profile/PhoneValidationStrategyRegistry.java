package com.stockify.identity.domain.profile;

import com.stockify.shared.exception.InvalidValueException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.util.*;

/**
 * Registry of {@link CountryRule} instances used to validate phone numbers by country.
 * <p>
 * Country rules are keyed by their calling code (e.g. {@code "+359"}, {@code "+49"}).
 * Lookup is attempted from longest to shortest code ({@code 4 → 3 → 2} characters) to
 * correctly resolve codes that share a common prefix.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PhoneValidationStrategyRegistry {

    private static final Map<String, CountryRule> REGISTRY;

    /**
     * Candidate prefix lengths tried in descending order to find the calling code.
     */
    private static final List<Integer> CODE_LENGTHS = List.of(4, 3, 2);

    static {
        Map<String, CountryRule> map = new HashMap<>();

        // Loads all CountryRule SPI implementations and indexes them by calling code.
        ServiceLoader.load(CountryRule.class)
                .forEach(countryRule -> map.put(countryRule.countryCode(), countryRule));

        REGISTRY = Collections.unmodifiableMap(map);
    }

    /**
     * Resolves the {@link CountryRule} for the given phone number and validates it.
     *
     * @param phoneNumber the phone number to validate, including its country calling code
     * @throws com.stockify.shared.exception.InvalidValueException if no matching country rule
     *                                                             is found, or the number does not match the country's expected format
     */
    public static void resolve(@NonNull String phoneNumber) {
        extractCountryCode(phoneNumber).validate(phoneNumber);
    }

    /**
     * Extracts and returns the {@link CountryRule} matching the calling code prefix of
     * the given phone number.
     * <p>
     * Prefixes of length 4, 3, and 2 are tried in that order so that longer, more specific
     * codes (e.g. {@code "+359"}) take precedence over shorter overlapping ones.
     *
     * @param phoneNumber the phone number whose calling code should be identified
     * @return the matching {@link CountryRule}, never {@code null}
     * @throws com.stockify.shared.exception.InvalidValueException if no registered country
     *                                                             rule matches the phone number's prefix
     */
    private static @NonNull CountryRule extractCountryCode(@NonNull String phoneNumber) {
        for (int length : CODE_LENGTHS) {
            if (phoneNumber.length() >= length) {
                String candidate = phoneNumber.substring(0, length);
                CountryRule rule = REGISTRY.get(candidate);
                if (rule != null) return rule;
            }
        }
        throw new InvalidValueException(PhoneNumberMessages.INVALID_MSG, phoneNumber);
    }
}
