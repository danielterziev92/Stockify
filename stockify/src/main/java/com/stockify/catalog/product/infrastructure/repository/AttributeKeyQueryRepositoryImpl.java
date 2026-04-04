package com.stockify.catalog.product.infrastructure.repository;

import com.stockify.catalog.product.application.dto.AttributeKeyDto;
import com.stockify.catalog.product.application.dto.AttributeValueDto;
import com.stockify.catalog.product.domain.repository.AttributeKeyQueryRepository;
import com.stockify.catalog.product.domain.vo.AttributeKeyId;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectOnConditionStep;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.stockify.catalog.product.infrastructure.jooq.AttributeTables.*;
import static org.jooq.impl.DSL.lower;

@Repository
@RequiredArgsConstructor
public class AttributeKeyQueryRepositoryImpl implements AttributeKeyQueryRepository {

    private final AttributeKeyJdbcRepository jdbcRepository;
    private final DSLContext dsl;

    @Override
    public List<AttributeKeyDto> findAll() {
        return buildQuery()
                .fetchGroups(KEY_ID)
                .values()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public Optional<AttributeKeyDto> findById(@NonNull AttributeKeyId id) {
        return this.buildQuery()
                .where(KEY_ID.eq(id.value()))
                .fetchGroups(KEY_ID)
                .values()
                .stream()
                .map(this::toDto)
                .findFirst();
    }

    @Override
    public List<AttributeKeyDto> searchByName(@NonNull String name) {
        return buildQuery()
                .where(lower(KEY_NAME).like("%" + name.toLowerCase() + "%"))
                .fetchGroups(KEY_ID)
                .values()
                .stream()
                .map(this::toDto)
                .toList();
    }

    private @NonNull SelectOnConditionStep<?> buildQuery() {
        return dsl
                .select(KEY_ID, KEY_NAME, VALUE_ID, VALUE_VALUE, VALUE_ABBR)
                .from(ATTRIBUTE_KEY)
                .leftJoin(ATTRIBUTE_VALUE)
                .on(VALUE_KEY_ID.eq(KEY_ID));
    }

    private @NonNull AttributeKeyDto toDto(@NonNull Result<?> records) {
        Record first = records.getFirst();

        List<AttributeValueDto> values = records.stream()
                .filter(record -> record.get(VALUE_ID) != null)
                .map(record -> new AttributeValueDto(
                        record.get(VALUE_ID),
                        record.get(VALUE_VALUE),
                        record.get(VALUE_ABBR)
                )).toList();

        return new AttributeKeyDto(first.get(KEY_ID), first.get(KEY_NAME), values);
    }
}
