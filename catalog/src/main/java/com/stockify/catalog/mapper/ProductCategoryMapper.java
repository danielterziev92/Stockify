package com.stockify.catalog.mapper;

import com.stockify.catalog.dto.CategoryDTO;
import com.stockify.catalog.model.ProductCategory;
import com.stockify.catalog.response.ProductCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductCategoryMapper extends BaseCategoryMapper<ProductCategory, ProductCategoryResponse> {
    @Mapping(target = "parentId", source = "parent.id")
    ProductCategoryResponse toResponse(ProductCategory category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "children", ignore = true)
    ProductCategory toEntity(CategoryDTO categoryDTO);
}
