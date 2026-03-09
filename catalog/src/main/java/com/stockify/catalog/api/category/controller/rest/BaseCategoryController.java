package com.stockify.catalog.api.category.controller.rest;

import com.stockify.catalog.api.category.response.PageMetaResponse;
import com.stockify.catalog.api.category.response.PageResponse;
import com.stockify.catalog.application.category.dto.CategoryDTOs;
import com.stockify.catalog.application.category.usecase.CategoryApplicationService;
import com.stockify.catalog.domain.category.model.Category;
import com.stockify.catalog.openapi.CategoryPageableAsQueryParam;
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
public abstract class BaseCategoryController<R, S extends CategoryApplicationService<? extends Category, R>> {

    protected final S service;

    @GetMapping
    public ResponseEntity<PageResponse<R>> getAll(
            @RequestParam(required = false) Boolean active,
            @Parameter(
                    description = "Pagination and sorting parameters",
                    schema = @Schema(implementation = CategoryPageableAsQueryParam.class)
            )
            @PageableDefault(sort = {"displayOrder", "id"}, direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<R> page = active != null
                ? this.service.getAllByActive(active, pageable)
                : this.service.getAll(pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new PageResponse<>(PageMetaResponse.from(page), page.getContent()));
    }

    @GetMapping("/search")
    public ResponseEntity<List<R>> search(
            @RequestParam String name,
            @RequestParam(required = false, defaultValue = "true") Boolean active
    ) {
        return ResponseEntity.ok(this.service.search(name, active));
    }

    @GetMapping("/{id}")
    public ResponseEntity<R> getById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.getById(id));
    }

    @PostMapping
    public ResponseEntity<R> create(@Valid @RequestBody CategoryDTOs.CreateCategoryCommand command) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.service.create(command));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<R> update(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDTOs.UpdateCategoryCommand command
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.update(id, command));
    }

    @PatchMapping("/{id}/move")
    public ResponseEntity<R> move(
            @PathVariable Long id,
            @RequestParam(name = "parentId", required = false) Long parentId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.move(id, parentId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.service.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
