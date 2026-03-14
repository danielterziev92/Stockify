package com.stockify.catalog.controller.rest.category;

import com.stockify.catalog.openapi.CategoryPageableAsQueryParam;
import com.stockify.catalog.dto.CategoryDTO;
import com.stockify.catalog.dto.PatchCategoryDTO;
import com.stockify.catalog.response.PageMetaResponse;
import com.stockify.catalog.response.PageResponse;
import com.stockify.catalog.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Get all categories")
    @Parameter(
            name = "pageable",
            required = true,
            description = "Pagination and sorting parameters",
            schema = @Schema(implementation = CategoryPageableAsQueryParam.class)
    )
    @GetMapping(version = "1.0")
    public ResponseEntity<PageResponse<R>> getAllCategories(
            @RequestParam(name = "active", required = false) Boolean active,
            @Parameter(hidden = true)
            @PageableDefault(sort = {"displayOrder", "id"}, direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<R> page = active != null
                ? this.service.getAllByActive(active, pageable)
                : this.service.getAll(pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new PageResponse<>(PageMetaResponse.from(page), page.getContent()));
    }

    @Operation(summary = "Search categories by name")
    @GetMapping(value = "/search", version = "1.0")
    public ResponseEntity<List<R>> search(
            @RequestParam String name,
            @RequestParam(name = "active", required = false, defaultValue = "true") Boolean active
    ) {
        return ResponseEntity.ok(this.service.search(name, active));
    }

    @Operation(summary = "Get category with its direct children")
    @GetMapping(value = "/{id}", version = "1.0")
    public ResponseEntity<List<R>> getCategoryWithChildren(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.getByIdWithChildren(id));
    }

    @Operation(summary = "Create a new category")
    @PostMapping(version = "1.0")
    public ResponseEntity<R> createCategory(@Valid @RequestBody CategoryDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.service.create(dto));
    }

    @Operation(summary = "Partially update a category")
    @PatchMapping(value = "/{id}", version = "1.0")
    public ResponseEntity<R> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody PatchCategoryDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.update(id, dto));
    }

    @Operation(summary = "Move a category to a new parent")
    @PatchMapping(value = "/{id}/move", version = "1.0")
    public ResponseEntity<R> moveCategory(
            @PathVariable Long id,
            @RequestParam(name = "parentId", required = false) Long parentId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.move(id, parentId));
    }

    @Operation(summary = "Delete a category and its descendants")
    @DeleteMapping(value = "/{id}", version = "1.0")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        this.service.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
