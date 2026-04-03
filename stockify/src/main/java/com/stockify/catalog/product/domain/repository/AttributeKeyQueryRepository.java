package com.stockify.catalog.product.domain.repository;

import com.stockify.catalog.product.application.dto.AttributeKeyDto;

import java.util.List;
import java.util.Optional;

public interface AttributeKeyQueryRepository {

    List<AttributeKeyDto> findAll();

    Optional<AttributeKeyDto> findById(String id);

    List<AttributeKeyDto> searchByName(String name);
}
