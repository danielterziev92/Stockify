package com.stockify.catalog.application.product.query;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AttributeKeyResponse {

    public record KeySummary(
            @NonNull UUID id,
            @NonNull String name,
            @NonNull List<ValueSummary> values
    ) {
    }

    public record ValueSummary(
            @NonNull UUID id,
            @NonNull String value,
            @NonNull String abbreviation
    ) {
    }
}
