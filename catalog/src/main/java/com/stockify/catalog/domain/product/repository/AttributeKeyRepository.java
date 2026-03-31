package com.stockify.catalog.domain.product.repository;

import com.stockify.catalog.domain.product.AttributeKey;
import com.stockify.catalog.domain.product.vo.AttributeKeyId;
import com.stockify.catalog.domain.product.vo.AttributeValueId;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttributeKeyRepository extends org.jmolecules.ddd.types.Repository<AttributeKey, AttributeKeyId> {

    List<AttributeKey> findAll();

    Optional<AttributeKey> findById(@NonNull AttributeKeyId id);

    void save(@NonNull AttributeKey attributeKey);

    boolean existsById(@NonNull AttributeKeyId id);

    boolean existsByName(@NonNull String name);

    Optional<AttributeKey> findByValueId(@NonNull AttributeValueId valueId);
}
