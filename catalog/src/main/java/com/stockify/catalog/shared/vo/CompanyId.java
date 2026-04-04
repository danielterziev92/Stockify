package com.stockify.catalog.shared.vo;

import org.jmolecules.ddd.types.ValueObject;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

public record CompanyId(@NonNull UUID value) implements ValueObject {

    public static @NonNull CompanyId of(@NonNull UUID value) {
        return new CompanyId(value);
    }

    public static @NonNull CompanyId of(@NonNull String value) {
        return new CompanyId(UUID.fromString(value));
    }
}
