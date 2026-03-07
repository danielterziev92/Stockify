package com.stockify.catalog.service.impl;

import com.stockify.catalog.constants.CategoryConstants;
import com.stockify.catalog.dto.CategoryDTO;
import com.stockify.catalog.mapper.BaseCategoryMapper;
import com.stockify.catalog.model.Category;
import com.stockify.catalog.repository.CategoryRepository;
import com.stockify.catalog.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@RequiredArgsConstructor
public abstract class BaseCategoryServiceImpl<T extends Category, R, REPO extends CategoryRepository<T>, MAPPER extends BaseCategoryMapper<T, R>>
        implements CategoryService<R> {

    protected final REPO repository;
    protected final MAPPER mapper;

    @Override
    @Transactional(readOnly = true)
    public Page<R> getAll(@NonNull Pageable pageable) {
        return this.repository.findAll(pageable)
                .map(this.mapper::toResponse);
    }

    @Override
    public Page<R> getAllByActive(boolean active, @NonNull Pageable pageable) {
        return this.repository.findAllByActive(active, pageable)
                .map(this.mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public R getById(@NonNull Long id) {
        return this.repository.findById(id)
                .map(this.mapper::toResponse)
                .orElseThrow(() -> this.categoryNotFound(id));
    }

    @Override
    @Transactional
    public R create(@NonNull CategoryDTO categoryDTO) {
        T category = this.mapper.toEntity(categoryDTO);

        category.setActive(categoryDTO.active() != null ? categoryDTO.active() : true);
        category.setDisplayOrder(categoryDTO.displayOrder() != null ? categoryDTO.displayOrder() : 0);

        if (categoryDTO.parentId() != null) this.checkAndSetParent(category, categoryDTO.parentId());

        return this.saveAndResponse(category);
    }

    @Override
    @Transactional
    public R update(@NonNull Long id, @NonNull CategoryDTO categoryDTO) {
        T category = this.repository.findById(id)
                .orElseThrow(() -> this.categoryNotFound(id));

        boolean nameChanged = !category.getName().equals(categoryDTO.name());
        boolean activeChange = !category.getActive().equals(categoryDTO.active());
        boolean displayOrderChanged = !category.getDisplayOrder().equals(categoryDTO.displayOrder());

        Long currentParentId = category.getParent() != null ? category.getParent().getId() : null;
        boolean parentChanged = !Objects.equals(currentParentId, categoryDTO.parentId());

        if (!nameChanged && !activeChange && !displayOrderChanged && !parentChanged)
            return this.mapper.toResponse(category);

        if (nameChanged) category.setName(categoryDTO.name());
        if (activeChange) category.setActive(categoryDTO.active());
        if (displayOrderChanged) category.setDisplayOrder(categoryDTO.displayOrder());
        if (parentChanged) this.checkAndSetParent(category, categoryDTO.parentId());

        return this.saveAndResponse(category);
    }

    @Override
    @Transactional
    public R move(@NonNull Long id, Long newParentId) {
        T category = this.repository.findById(id)
                .orElseThrow(() -> this.categoryNotFound(id));

        Category newParent = null;
        if (newParentId != null) {
            if (newParentId.equals(id))
                throw new IllegalArgumentException(CategoryConstants.CATEGORY_OWN_PARENT_MESSAGE);

            if (this.repository.isDescendant(id, newParentId))
                throw new IllegalArgumentException(CategoryConstants.CATEGORY_CIRCULAR_REFERENCE_MESSAGE);

            newParent = this.repository.getReferenceById(newParentId);
        }

        category.setParent(newParent);

        return this.saveAndResponse(category);
    }

    @Override
    @Transactional
    public void delete(@NonNull Long id) {
        this.repository.delete(
                this.repository.findById(id).orElseThrow(() -> this.categoryNotFound(id))
        );
    }

    @Contract("_ -> new")
    private @NonNull EntityNotFoundException categoryNotFound(@NonNull Long id) {
        return new EntityNotFoundException(CategoryConstants.CATEGORY_NOT_FOUND_BY_ID_MESSAGE.formatted(id));
    }

    private void checkAndSetParent(@NonNull T category, @NonNull Long parentId) {
        if (!this.repository.existsById(parentId))
            throw this.categoryNotFound(parentId);

        category.setParent(this.repository.getReferenceById(parentId));
    }

    private @NonNull R saveAndResponse(@NonNull T category) {
        return this.mapper.toResponse(this.repository.save(category));
    }
}
