package com.stockify.catalog.controller.rest;

import com.stockify.catalog.openapi.CategoryPageableAsQueryParam;
import com.stockify.catalog.dto.CategoryDTO;
import com.stockify.catalog.dto.PatchCategoryDTO;
import com.stockify.catalog.response.PageMetaResponse;
import com.stockify.catalog.response.PageResponse;
import com.stockify.catalog.service.CategoryService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
public abstract class BaseCategoryController<R, S extends CategoryService<R>> {

    protected final S service;

    @GetMapping(version = "1.0")
    public ResponseEntity<PageResponse<R>> getAllCategories(
            @RequestParam(name = "active", required = false) Boolean active,
            @Parameter(
                    description = "Pagination and sorting parameters",
                    schema = @Schema(implementation = CategoryPageableAsQueryParam.class)
            )
            @PageableDefault(sort = {"displayOrder", "id"}, direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<R> page;

        if (active != null) {
            page = this.service.getAllByActive(active, pageable);
        } else {
            page = this.service.getAll(pageable);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new PageResponse<>(PageMetaResponse.from(page), page.getContent()));
    }

    @GetMapping(value = "/search", version = "1.0")
    public ResponseEntity<List<R>> search(
            @RequestParam String name,
            @RequestParam(name = "active", required = false, defaultValue = "true") Boolean active
    ) {
        return ResponseEntity.ok(this.service.search(name, active));
    }

    @GetMapping(value = "/{id}", version = "1.0")
    public ResponseEntity<R> getCategoryById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.getById(id));
    }

    @PostMapping(version = "1.0")
    public ResponseEntity<R> createCategory(@Valid @RequestBody CategoryDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.service.create(dto));
    }

    @PatchMapping(value = "/{id}", version = "1.0")
    public ResponseEntity<R> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody PatchCategoryDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.update(id, dto));
    }

    @PatchMapping(value = "/{id}/move", version = "1.0")
    public ResponseEntity<R> moveCategory(
            @PathVariable Long id,
            @RequestParam(name = "parentId", required = false) Long parentId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.move(id, parentId));
    }

    @DeleteMapping(value = "/{id}", version = "1.0")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        this.service.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
