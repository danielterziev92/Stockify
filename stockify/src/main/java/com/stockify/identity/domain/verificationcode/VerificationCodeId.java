package com.stockify.identity.domain.verificationcode;

import jakarta.annotation.Nonnull;
import org.jmolecules.ddd.types.Identifier;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * Identifier for the {@link VerificationCode} aggregate root.
 *
 * @param value the underlying UUID
 */
public record VerificationCodeId(@NonNull UUID value) implements Identifier {

    /**
     * Creates a new {@link VerificationCodeId} backed by a random UUID.
     */
    public static @NonNull VerificationCodeId generate() {
        return new VerificationCodeId(UUID.randomUUID());
    }

    /**
     * Creates a {@link VerificationCodeId} from an existing {@link UUID}.
     *
     * @param value the UUID to wrap
     */
    public static @NonNull VerificationCodeId of(@NonNull UUID value) {
        return new VerificationCodeId(value);
    }

    /**
     * Creates a {@link VerificationCodeId} by parsing a UUID string.
     *
     * @param value the UUID string to parse
     * @throws IllegalArgumentException if {@code value} is not a valid UUID
     */
    public static @NonNull VerificationCodeId of(@Nonnull String value) {
        return new VerificationCodeId(UUID.fromString(value));
    }
}
