package com.stockify.catalog.product.application.query;

import com.stockify.catalog.product.domain.vo.AttributeKeyId;
import org.jspecify.annotations.NonNull;

public sealed interface AttributeKeyQuery permits
        AttributeKeyQuery.FindAll,
        AttributeKeyQuery.FindById,
        AttributeKeyQuery.SearchByName {

    record FindAll() implements AttributeKeyQuery {
    }

    record FindById(@NonNull AttributeKeyId id) implements AttributeKeyQuery {
    }

    record SearchByName(@NonNull String name) implements AttributeKeyQuery {
    }
}
