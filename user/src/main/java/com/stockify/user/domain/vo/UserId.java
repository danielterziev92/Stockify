package com.stockify.user.domain.vo;

import org.jmolecules.ddd.types.Identifier;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

public record UserId(@NonNull UUID value) implements Identifier {
    public static @NonNull UserId generate() {
        return new UserId(UUID.randomUUID());
    }

    public static @NonNull UserId of(@NonNull UUID value) {
        return new UserId(value);
    }

    public static @NonNull UserId of(@NonNull String value) {
        return new UserId(UUID.fromString(value));
    }
}
