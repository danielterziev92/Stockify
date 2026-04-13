package com.stockify.authorization.domain.module;

import jakarta.annotation.Nonnull;
import org.jmolecules.ddd.types.Identifier;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * Strongly typed identifier for a {@code ModuleResource} entity.
 *
 * <p>Wraps a {@link UUID} and implements {@link Identifier} to integrate with
 * the jMolecules DDD type system. Use the static factory methods instead of
 * the canonical constructor to keep call sites readable.
 *
 * @param value the underlying UUID; must not be {@code null}
 */
public record ModuleResourceId(@NonNull UUID value) implements Identifier {

    /**
     * Creates a new {@code ModuleResourceId} backed by a randomly generated UUID.
     *
     * @return a new, unique identifier
     */
    public static @NonNull ModuleResourceId generate() {
        return new ModuleResourceId(UUID.randomUUID());
    }

    /**
     * Creates a {@code ModuleResourceId} from an existing {@link UUID}.
     *
     * @param value the UUID to wrap; must not be {@code null}
     * @return a {@code ModuleResourceId} wrapping the given value
     */
    public static @NonNull ModuleResourceId of(@NonNull UUID value) {
        return new ModuleResourceId(value);
    }

    /**
     * Creates a {@code ModuleResourceId} by parsing a UUID string.
     *
     * @param value the UUID string to parse; must not be {@code null}
     * @return a {@code ModuleResourceId} wrapping the parsed UUID
     * @throws IllegalArgumentException if {@code value} is not a valid UUID string
     */
    public static @NonNull ModuleResourceId of(@Nonnull String value) {
        return new ModuleResourceId(UUID.fromString(value));
    }
}
