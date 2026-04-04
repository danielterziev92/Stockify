package com.stockify.catalog.product.infrastructure.graphql;

import com.stockify.catalog.exception.EntityNotFoundException;
import com.stockify.catalog.product.application.dto.AttributeKeyDto;
import com.stockify.catalog.product.application.query.AttributeKeyQuery;
import com.stockify.catalog.product.domain.repository.AttributeKeyQueryRepository;
import com.stockify.catalog.product.domain.rule.AttributeRule;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttributeKeyQueryService {

    private final AttributeKeyQueryRepository queryRepository;

    public List<AttributeKeyDto> findAll(AttributeKeyQuery.@NonNull FindAll query) {
        return queryRepository.findAll();
    }

    public AttributeKeyDto findById(AttributeKeyQuery.@NonNull FindById query) {
        return queryRepository.findById(query.id())
                .orElseThrow(() -> new EntityNotFoundException(
                        AttributeRule.AttributeKey.Generic.NOT_FOUND_MSG, query.id()));
    }

    public List<AttributeKeyDto> searchByName(AttributeKeyQuery.@NonNull SearchByName query) {
        return queryRepository.searchByName(query.name());
    }
}
