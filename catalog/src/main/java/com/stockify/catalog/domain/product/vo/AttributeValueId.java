package com.stockify.catalog.domain.product.vo;

import org.jmolecules.ddd.types.Identifier;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

public record AttributeValueId(@NonNull UUID value) implements Identifier {
    public static @NonNull AttributeValueId generate() {
        return new AttributeValueId(UUID.randomUUID());
    }

    public static @NonNull AttributeValueId of(@NonNull UUID value) {
        return new AttributeValueId(value);
    }

    public static @NonNull AttributeValueId of(@NonNull String value) {
        return new AttributeValueId(UUID.fromString(value));
    }
}
