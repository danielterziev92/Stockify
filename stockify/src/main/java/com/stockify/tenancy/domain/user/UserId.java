package com.stockify.tenancy.domain.user;

import jakarta.annotation.Nonnull;
import org.jmolecules.ddd.types.Identifier;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * Identifier for the {@link User} aggregate root.
 *
 * <p>Wraps a {@link UUID} to provide a strongly typed identity that cannot be
 * confused with other UUID-based identifiers at compile time.
 *
 * @param value the underlying UUID
 */
public record UserId(@NonNull UUID value) implements Identifier {

    /**
     * Generates a new, random {@code UserId}.
     *
     * @return a new {@code UserId} backed by a random UUID
     */
    public static @NonNull UserId generate() {
        return new UserId(UUID.randomUUID());
    }

    /**
     * Creates a {@code UserId} from an existing {@link UUID}.
     *
     * @param value the UUID to wrap
     * @return a {@code UserId} backed by the given UUID
     */
    public static @NonNull UserId of(@NonNull UUID value) {
        return new UserId(value);
    }

    /**
     * Creates a {@code UserId} from a UUID string.
     *
     * @param value the string representation of the UUID
     * @return a {@code UserId} backed by the parsed UUID
     * @throws IllegalArgumentException if {@code value} is not a valid UUID string
     */
    public static @NonNull UserId of(@Nonnull String value) {
        return new UserId(UUID.fromString(value));
    }
}
