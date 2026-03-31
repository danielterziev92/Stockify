package com.stockify.catalog.infrastructure.product.persistence.persistence;

import com.stockify.catalog.infrastructure.product.persistence.record.AttributeKeyRecord;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataAttributeKeyRepository extends CrudRepository<AttributeKeyRecord, UUID> {

    boolean existsByNameIgnoreCase(String name);

    Optional<AttributeKeyRecord> findByValuesId(UUID valueId);
}
