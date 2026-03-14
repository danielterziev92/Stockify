package com.stockify.catalog.mapper.category;

import com.stockify.catalog.model.category.CategoryMV;

public interface CategoryMVMapper<R> {
    R toResponse(CategoryMV categoryMV);
}
