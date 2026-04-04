package com.stockify.catalog.product.domain.repository;

import com.stockify.catalog.product.application.dto.AttributeKeyDto;
import com.stockify.catalog.product.domain.vo.AttributeKeyId;

import java.util.List;
import java.util.Optional;

public interface AttributeKeyQueryRepository {

    List<AttributeKeyDto> findAll();

    Optional<AttributeKeyDto> findById(AttributeKeyId id);

    List<AttributeKeyDto> searchByName(String name);
}
