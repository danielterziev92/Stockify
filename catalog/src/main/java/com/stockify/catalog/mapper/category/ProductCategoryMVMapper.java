package com.stockify.catalog.mapper.category;

import com.stockify.catalog.model.category.CategoryMV;
import com.stockify.catalog.response.category.ProductCategoryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductCategoryMVMapper extends CategoryMVMapper<ProductCategoryResponse> {
    ProductCategoryResponse toResponse(CategoryMV categoryMV);
}
