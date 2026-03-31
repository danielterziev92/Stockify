package com.stockify.catalog.infrastructure.product.persistence.record;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table("attribute_keys")
public record AttributeKeyRecord(
        @Id UUID id,
        String name,
        @MappedCollection(idColumn = "key_id")
        List<AttributeValueRecord> values
) {
    public AttributeKeyRecord(UUID id, String name) {
        this(id, name, new ArrayList<>());
    }
}
