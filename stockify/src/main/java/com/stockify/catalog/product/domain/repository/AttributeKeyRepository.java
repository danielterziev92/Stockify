package com.stockify.catalog.product.domain.repository;

import com.stockify.catalog.product.domain.AttributeKey;
import com.stockify.catalog.product.domain.vo.AttributeKeyId;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

public interface AttributeKeyRepository {

    Optional<AttributeKey> findById(@NonNull AttributeKeyId id);

    void save(@NonNull AttributeKey attributeKey);

    void delete(@NonNull AttributeKey attributeKey);
}
