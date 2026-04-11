package com.stockify.identity.user.validation.phone;

import com.stockify.shared.exception.InvalidValueException;
import org.jspecify.annotations.NonNull;

import java.util.regex.Pattern;

/**
 * Defines the validation contract for a country-specific phone number format.
 * <p>
 * Implementations provide the country calling code and a regex pattern used
 * to validate phone numbers belonging to that country.
 */
public interface CountryRule {

    /**
     * Returns the country calling code (e.g. {@code "+1"} for the US, {@code "+44"} for the UK).
     *
     * @return the country calling code, never {@code null}
     */
    String countryCode();

    /**
     * Returns the regex {@link Pattern} used to validate a phone number for this country.
     *
     * @return the validation pattern, never {@code null}
     */
    Pattern pattern();

    /**
     * Validates the given phone number against this country's rules.
     * <p>
     * First checks that the number is not blank, then matches it against
     * {@link #pattern()}. Throws {@link InvalidValueException} on the first
     * violation encountered.
     *
     * @param phoneNumber the phone number to validate, including the country calling code
     * @throws InvalidValueException if the phone number is blank or does not match
     *         the expected format for this country
     */
    default void validate(@NonNull String phoneNumber) {
        if (phoneNumber.isBlank())
            throw new InvalidValueException(PhoneNumberMessages.BLANK_MSG);
        if (!pattern().matcher(phoneNumber).matches())
            throw new InvalidValueException(PhoneNumberMessages.INVALID_MSG, phoneNumber);
    }
}
