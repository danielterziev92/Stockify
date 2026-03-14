package com.stockify.catalog.mapper.category;

import com.stockify.catalog.dto.category.CategoryDTO;
import com.stockify.catalog.model.category.Category;

public interface BaseCategoryMapper<T extends Category, R> {

    R toResponse(T category);

    T toEntity(CategoryDTO categoryDTO);
}
