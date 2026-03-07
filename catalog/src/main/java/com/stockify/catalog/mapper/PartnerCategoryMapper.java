package com.stockify.catalog.mapper;

import com.stockify.catalog.dto.CategoryDTO;
import com.stockify.catalog.model.PartnerCategory;
import com.stockify.catalog.response.ProductCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PartnerCategoryMapper {
    @Mapping(target = "parentId", source = "parent.id")
    ProductCategoryResponse toResponse(PartnerCategory category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "children", ignore = true)
    PartnerCategory toEntity(CategoryDTO categoryDTO);
}
