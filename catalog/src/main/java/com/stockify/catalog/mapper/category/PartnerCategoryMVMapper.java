package com.stockify.catalog.mapper.category;

import com.stockify.catalog.model.category.CategoryMV;
import com.stockify.catalog.response.category.PartnerCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PartnerCategoryMVMapper extends CategoryMVMapper<PartnerCategoryResponse> {

    @Mapping(target = "parentId", source = "parentId")
    @Mapping(target = "displayOrder", source = "displayOrder")
    PartnerCategoryResponse toResponse(CategoryMV categoryMV);
}
