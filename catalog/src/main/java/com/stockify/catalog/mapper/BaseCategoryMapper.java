package com.stockify.catalog.mapper;

import com.stockify.catalog.dto.CategoryDTO;
import com.stockify.catalog.model.Category;

public interface BaseCategoryMapper<T extends Category, R> {

    R toResponse(T category);

    T toEntity(CategoryDTO categoryDTO);
}
