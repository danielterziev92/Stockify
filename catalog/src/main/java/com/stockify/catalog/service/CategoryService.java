package com.stockify.catalog.service;

import com.stockify.catalog.dto.CategoryDTO;
import com.stockify.catalog.response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    Page<CategoryResponse> getAll(Pageable pageable);

    Page<CategoryResponse> getAllByActive(boolean active, Pageable pageable);

    CategoryResponse getById(Long id);

    CategoryResponse create(CategoryDTO categoryDTO);

    CategoryResponse update(Long id, CategoryDTO categoryDTO);

    CategoryResponse move(Long id, Long newParentId);

    void delete(Long id);
}
