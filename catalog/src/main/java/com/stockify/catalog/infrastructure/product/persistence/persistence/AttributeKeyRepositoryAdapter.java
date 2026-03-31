package com.stockify.catalog.infrastructure.product.persistence.persistence;

import com.stockify.catalog.domain.product.AttributeKey;
import com.stockify.catalog.domain.product.AttributeValue;
import com.stockify.catalog.domain.product.repository.AttributeKeyRepository;
import com.stockify.catalog.domain.product.vo.AttributeKeyId;
import com.stockify.catalog.domain.product.vo.AttributeKeyName;
import com.stockify.catalog.domain.product.vo.AttributeValueId;
import com.stockify.catalog.infrastructure.product.persistence.record.AttributeKeyRecord;
import com.stockify.catalog.infrastructure.product.persistence.record.AttributeValueRecord;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor
public class AttributeKeyRepositoryAdapter implements AttributeKeyRepository {

    private final SpringDataAttributeKeyRepository repository;

    @Override
    public List<AttributeKey> findAll() {
        return StreamSupport.stream(this.repository.findAll().spliterator(), false)
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<AttributeKey> findById(@NonNull AttributeKeyId id) {
        return Optional.empty();
    }

    @Override
    public void save(@NonNull AttributeKey attributeKey) {

    }

    @Override
    public boolean existsById(@NonNull AttributeKeyId id) {
        return false;
    }

    @Override
    public boolean existsByName(@NonNull String name) {
        return false;
    }

    @Override
    public Optional<AttributeKey> findByValueId(@NonNull AttributeValueId valueId) {
        return Optional.empty();
    }

    private @NonNull AttributeKey toDomain(@NonNull AttributeKeyRecord record) {
        List<AttributeValue> values = record.values().stream()
                .map(v -> toDomainValue(v, record.id()))
                .toList();

        return AttributeKey.reconstitute(
                AttributeKeyId.of(record.id()),
                new AttributeKeyName(record.name()),
                values
        );
    }

    private @NonNull AttributeValue toDomainValue(
            @NonNull AttributeValueRecord record,
            @NonNull UUID keyId
    ) {
        return AttributeValue.reconstitute(
                AttributeValueId.of(record.id()),
                AttributeKeyId.of(keyId),
                record.value(),
                record.abbreviation()
        );
    }
}
