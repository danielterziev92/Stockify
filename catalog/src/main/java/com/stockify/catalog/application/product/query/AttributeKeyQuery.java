package com.stockify.catalog.application.product.query;

import org.jspecify.annotations.NonNull;

import java.util.UUID;

public sealed interface AttributeKeyQuery permits
        AttributeKeyQuery.GetAll,
        AttributeKeyQuery.GetById {
    record GetAll() implements AttributeKeyQuery {
    }

    record GetById(@NonNull UUID keyId) implements AttributeKeyQuery {
    }
}
