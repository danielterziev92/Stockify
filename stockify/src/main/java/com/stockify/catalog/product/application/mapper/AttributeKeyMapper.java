package com.stockify.catalog.product.application.mapper;

import com.stockify.catalog.product.application.dto.AttributeKeyDto;
import com.stockify.catalog.product.application.dto.AttributeValueDto;
import com.stockify.catalog.product.domain.AttributeKey;
import com.stockify.catalog.product.domain.AttributeValue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttributeKeyMapper {

    @Mapping(target = "id", source = "id.value")
    AttributeKeyDto toDto(AttributeKey key);

    @Mapping(target = "id", source = "id.value")
    AttributeValueDto toDto(AttributeValue value);
}
