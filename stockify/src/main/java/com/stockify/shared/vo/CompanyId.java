package com.stockify.shared.vo;

import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * Strongly typed identifier for a company.
 *
 * @param value the underlying UUID; must not be {@code null}
 */
public record CompanyId(@NonNull UUID value) {

    /**
     * Creates a new {@code CompanyId} backed by a randomly generated UUID.
     *
     * @return a new, unique {@code CompanyId}
     */
    public static @NonNull CompanyId generate() {
        return new CompanyId(UUID.randomUUID());
    }

    /**
     * Creates a {@code CompanyId} from an existing {@link UUID}.
     *
     * @param value the UUID to wrap; must not be {@code null}
     * @return a {@code CompanyId} wrapping the given value
     */
    public static @NonNull CompanyId of(@NonNull UUID value) {
        return new CompanyId(value);
    }

    /**
     * Creates a {@code CompanyId} by parsing a UUID string.
     *
     * @param value the UUID string to parse; must not be {@code null}
     * @return a {@code CompanyId} wrapping the parsed UUID
     * @throws IllegalArgumentException if {@code value} is not a valid UUID string
     */
    public static @NonNull CompanyId of(@NonNull String value) {
        return new CompanyId(UUID.fromString(value));
    }
}
