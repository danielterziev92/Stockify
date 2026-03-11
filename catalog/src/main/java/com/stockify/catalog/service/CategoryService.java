package com.stockify.catalog.service;

import com.stockify.catalog.dto.CategoryDTO;
import com.stockify.catalog.dto.PatchCategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService<T> {

    Page<T> getAll(Pageable pageable);

    Page<T> getAllByActive(boolean active, Pageable pageable);

    List<T> search(String name, Boolean active);

    T getById(Long id);

    T create(CategoryDTO categoryDTO);

    T update(Long id, PatchCategoryDTO categoryDTO);

    T move(Long id, Long newParentId);

    void delete(Long id);
}
