package com.stockify.catalog.product.infrastructure.repository;

import com.stockify.catalog.product.domain.AttributeKey;
import com.stockify.catalog.product.domain.vo.AttributeKeyId;
import org.springframework.data.repository.CrudRepository;

public interface AttributeKeyJdbcRepository extends CrudRepository<AttributeKey, AttributeKeyId> {
}
