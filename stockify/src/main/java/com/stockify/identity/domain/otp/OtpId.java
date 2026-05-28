package com.stockify.identity.domain.otp;

import jakarta.annotation.Nonnull;
import org.jmolecules.ddd.types.Identifier;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * Identifier for the {@link Otp} aggregate root.
 *
 * @param value the underlying UUID
 */
public record OtpId(@NonNull UUID value) implements Identifier {

    /**
     * Creates a new {@code OtpId} backed by a randomly generated UUID.
     *
     * @return a new, unique {@code OtpId}
     */
    public static @NonNull OtpId generate() {
        return new OtpId(UUID.randomUUID());
    }

    /**
     * Creates an {@code OtpId} from an existing {@link UUID}.
     *
     * @param value the UUID to wrap; must not be {@code null}
     * @return an {@code OtpId} wrapping the given value
     */
    public static @NonNull OtpId of(@NonNull UUID value) {
        return new OtpId(value);
    }

    /**
     * Creates an {@code OtpId} by parsing a UUID string.
     *
     * @param value the UUID string to parse; must not be {@code null}
     * @return an {@code OtpId} wrapping the parsed UUID
     * @throws IllegalArgumentException if {@code value} is not a valid UUID string
     */
    public static @NonNull OtpId of(@Nonnull String value) {
        return new OtpId(UUID.fromString(value));
    }
}
