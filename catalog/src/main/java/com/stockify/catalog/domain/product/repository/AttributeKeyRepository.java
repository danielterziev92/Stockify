package com.stockify.catalog.domain.product.repository;

import com.stockify.catalog.domain.product.AttributeKey;
import com.stockify.catalog.domain.product.vo.AttributeKeyId;
import com.stockify.catalog.domain.product.vo.AttributeValueId;
import org.jmolecules.ddd.types.Repository;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;

public interface AttributeKeyRepository extends Repository<AttributeKey, AttributeKeyId> {

    List<AttributeKey> findAll();

    Optional<AttributeKey> findById(@NonNull AttributeKeyId id);

    void save(@NonNull AttributeKey attributeKey);

    boolean existsById(@NonNull AttributeKeyId id);

    boolean existsByName(@NonNull String name);

    Optional<AttributeKey> findByValueId(@NonNull AttributeValueId valueId);
}
