package com.stockify.catalog.mapper;

import com.stockify.catalog.dto.CategoryDTO;
import com.stockify.catalog.model.Category;
import com.stockify.catalog.response.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "parentId", source = "parent.id")
    CategoryResponse toResponse(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "children", ignore = true)
    Category toEntity(CategoryDTO categoryDTO);
}
