package com.stockify.catalog.product.infrastructure.repository;

import com.stockify.catalog.product.domain.AttributeKey;
import com.stockify.catalog.product.domain.repository.AttributeKeyRepository;
import com.stockify.catalog.product.domain.vo.AttributeKeyId;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AttributeKeyRepositoryImpl implements AttributeKeyRepository {

    private final AttributeKeyJdbcRepository jdbcRepository;

    @Override
    public Optional<AttributeKey> findById(@NonNull AttributeKeyId id) {
        return this.jdbcRepository.findById(id);
    }

    @Override
    public void save(@NonNull AttributeKey attributeKey) {
        this.jdbcRepository.save(attributeKey);
    }

    @Override
    public void delete(@NonNull AttributeKey attributeKey) {
        this.jdbcRepository.delete(attributeKey);
    }
}
