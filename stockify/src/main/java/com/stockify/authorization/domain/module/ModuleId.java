package com.stockify.authorization.domain.module;

import jakarta.annotation.Nonnull;
import org.jmolecules.ddd.types.Identifier;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * Strongly typed identifier for the {@code Module} aggregate root.
 *
 * <p>Wraps a {@link UUID} and implements {@link Identifier} to integrate with
 * the jMolecules DDD type system. Use the static factory methods instead of
 * the canonical constructor to keep call sites readable.
 *
 * @param value the underlying UUID; must not be {@code null}
 */
public record ModuleId(@NonNull UUID value) implements Identifier {

    /**
     * Creates a new {@code ModuleId} backed by a randomly generated UUID.
     *
     * @return a new, unique identifier
     */
    public static @NonNull ModuleId generate() {
        return new ModuleId(UUID.randomUUID());
    }

    /**
     * Creates a {@code ModuleId} from an existing {@link UUID}.
     *
     * @param value the UUID to wrap; must not be {@code null}
     * @return a {@code ModuleId} wrapping the given value
     */
    public static @NonNull ModuleId of(@NonNull UUID value) {
        return new ModuleId(value);
    }

    /**
     * Creates a {@code ModuleId} by parsing a UUID string.
     *
     * @param value the UUID string to parse; must not be {@code null}
     * @return a {@code ModuleId} wrapping the parsed UUID
     * @throws IllegalArgumentException if {@code value} is not a valid UUID string
     */
    public static @NonNull ModuleId of(@Nonnull String value) {
        return new ModuleId(UUID.fromString(value));
    }
}
