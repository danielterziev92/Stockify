package com.stockify.catalog.service;

import com.stockify.catalog.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService<T> {

    Page<T> getAll(Pageable pageable);

    Page<T> getAllByActive(boolean active, Pageable pageable);

    T getById(Long id);

    T create(CategoryDTO categoryDTO);

    T update(Long id, CategoryDTO categoryDTO);

    T move(Long id, Long newParentId);

    void delete(Long id);
}
