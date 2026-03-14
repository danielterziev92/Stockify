package com.stockify.catalog.mapper.category;

import com.stockify.catalog.dto.category.CategoryDTO;
import com.stockify.catalog.model.category.PartnerCategory;
import com.stockify.catalog.response.category.PartnerCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PartnerCategoryMapper extends BaseCategoryMapper<PartnerCategory, PartnerCategoryResponse> {

    @Mapping(target = "parentId", source = "parent.id")
    @Mapping(target = "children", ignore = true)
    PartnerCategoryResponse toResponse(PartnerCategory category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "children", ignore = true)
    PartnerCategory toEntity(CategoryDTO categoryDTO);
}
