package com.stockify.catalog.infrastructure.product.persistence.record;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("attribute_values")
public record AttributeValueRecord(
        @Id UUID id,
        String value,
        String abbreviation
) {
}
