package com.stockify.catalog.controller;

import com.stockify.catalog.dto.CategoryDTO;
import com.stockify.catalog.response.PageMetaResponse;
import com.stockify.catalog.response.PageResponse;
import com.stockify.catalog.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
public abstract class BaseCategoryController<R, S extends CategoryService<R>> {

    protected final S service;

    @GetMapping
    public ResponseEntity<PageResponse<R>> getAllCategories(
            @RequestParam(name = "active", required = false, defaultValue = "true") Boolean active,
            @PageableDefault(sort = {"displayOrder", "id"}, direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<R> page;

        if (active != null && active) {
            page = this.service.getAllByActive(true, pageable);
        } else {
            page = this.service.getAll(pageable);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new PageResponse<>(PageMetaResponse.from(page), page.getContent()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<R> getCategoryById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.getById(id));
    }

    @PostMapping
    public ResponseEntity<R> createCategory(@Valid @RequestBody CategoryDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.service.create(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<R> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.update(id, dto));
    }

    @PatchMapping("/{id}/move")
    public ResponseEntity<R> moveCategory(
            @PathVariable Long id,
            @RequestParam(name = "parentId", required = false) Long parentId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.move(id, parentId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        this.service.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
