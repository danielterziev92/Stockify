package com.stockify.identity.user.validation.phone;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Message keys used for phone number validation errors.
 * <p>
 * These keys are intended to be resolved against a message source
 * (e.g. {@code messages.properties}) to produce localized error messages.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PhoneNumberMessages {

    /** Key for the error raised when a phone number is blank. */
    public static final String BLANK_MSG = "user.phone.blank";

    /** Key for the error raised when a phone number does not match the expected format. */
    public static final String INVALID_MSG = "user.phone.invalid";
}
