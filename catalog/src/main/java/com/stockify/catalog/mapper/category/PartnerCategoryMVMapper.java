package com.stockify.catalog.mapper.category;

import com.stockify.catalog.model.category.CategoryMV;
import com.stockify.catalog.response.category.PartnerCategoryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PartnerCategoryMVMapper extends CategoryMVMapper<PartnerCategoryResponse> {
    PartnerCategoryResponse toResponse(CategoryMV categoryMV);
}
