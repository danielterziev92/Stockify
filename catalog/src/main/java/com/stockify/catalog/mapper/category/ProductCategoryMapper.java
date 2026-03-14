package com.stockify.catalog.mapper.category;

import com.stockify.catalog.dto.category.CategoryDTO;
import com.stockify.catalog.model.category.ProductCategory;
import com.stockify.catalog.response.category.ProductCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductCategoryMapper extends BaseCategoryMapper<ProductCategory, ProductCategoryResponse> {
    @Mapping(target = "parentId", source = "parent.id")
    @Mapping(target = "children", ignore = true)
    ProductCategoryResponse toResponse(ProductCategory category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "children", ignore = true)
    ProductCategory toEntity(CategoryDTO categoryDTO);
}
