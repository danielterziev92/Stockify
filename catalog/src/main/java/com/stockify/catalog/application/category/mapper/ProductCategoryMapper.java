package com.stockify.catalog.application.category.mapper;

import com.stockify.catalog.domain.category.model.ProductCategory;
import com.stockify.catalog.api.category.response.ProductCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductCategoryMapper {
    @Mapping(target = "parentId", source = "parent.id")
    ProductCategoryResponse toResponse(ProductCategory category);
}
