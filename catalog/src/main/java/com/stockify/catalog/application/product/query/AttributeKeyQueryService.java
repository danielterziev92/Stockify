package com.stockify.catalog.application.product.query;

import com.stockify.catalog.domain.product.AttributeKey;
import com.stockify.catalog.domain.product.AttributeValue;
import com.stockify.catalog.domain.product.repository.AttributeKeyRepository;
import com.stockify.catalog.domain.product.rule.AttributeRule;
import com.stockify.catalog.domain.product.vo.AttributeKeyId;
import com.stockify.catalog.shared.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttributeKeyQueryService {

    private final AttributeKeyRepository repository;

    @Cacheable("attribute-keys")
    public @NonNull List<AttributeKeyResponse.KeySummary> handle(AttributeKeyQuery.@NonNull GetAll query) {
        return this.repository.findAll()
                .stream()
                .map(this::toSummary)
                .toList();
    }

    public AttributeKeyResponse.@NonNull KeySummary handle(AttributeKeyQuery.@NonNull GetById query) {
        return this.repository.findById(AttributeKeyId.of(query.keyId()))
                .map(this::toSummary)
                .orElseThrow(
                        () -> new EntityNotFoundException(AttributeRule.AttributeKey.Generic.NOT_FOUND_MSG, query.keyId().toString())
                );
    }

    @CacheEvict(value = "attribute-keys", allEntries = true)
    public void evictCache() {
    }

    private AttributeKeyResponse.@NonNull KeySummary toSummary(@NonNull AttributeKey key) {
        List<AttributeKeyResponse.ValueSummary> values = key.getValues().stream()
                .map(this::toValueSummary)
                .toList();

        return new AttributeKeyResponse.KeySummary(
                key.getId().value(),
                key.getName().value(),
                values
        );
    }

    private AttributeKeyResponse.@NonNull ValueSummary toValueSummary(@NonNull AttributeValue value) {
        return new AttributeKeyResponse.ValueSummary(
                value.getId().value(),
                value.getValue(),
                value.getAbbreviation()
        );
    }
}
