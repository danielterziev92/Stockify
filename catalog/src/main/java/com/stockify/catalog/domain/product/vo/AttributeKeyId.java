package com.stockify.catalog.domain.product.vo;

import org.jmolecules.ddd.types.Identifier;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

public record AttributeKeyId(@NonNull UUID value) implements Identifier {
    public static @NonNull AttributeKeyId generate() {
        return new AttributeKeyId(UUID.randomUUID());
    }

    public static @NonNull AttributeKeyId of(@NonNull UUID value) {
        return new AttributeKeyId(value);
    }

    public static @NonNull AttributeKeyId of(@NonNull String value) {
        return new AttributeKeyId(UUID.fromString(value));
    }
}
