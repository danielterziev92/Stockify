package com.stockify.catalog.application.category.mapper;

import com.stockify.catalog.dto.CategoryDTO;
import com.stockify.catalog.domain.category.model.PartnerCategory;
import com.stockify.catalog.api.category.response.PartnerCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PartnerCategoryMapper {
    @Mapping(target = "parentId", source = "parent.id")
    PartnerCategoryResponse toResponse(PartnerCategory category);
}
