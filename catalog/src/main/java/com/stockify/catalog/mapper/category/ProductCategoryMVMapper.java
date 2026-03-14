package com.stockify.catalog.mapper.category;

import com.stockify.catalog.model.category.CategoryMV;
import com.stockify.catalog.response.category.ProductCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductCategoryMVMapper extends CategoryMVMapper<ProductCategoryResponse> {

    @Mapping(target = "parentId", source = "parentId")
    @Mapping(target = "displayOrder", source = "displayOrder")
    ProductCategoryResponse toResponse(CategoryMV categoryMV);
}
