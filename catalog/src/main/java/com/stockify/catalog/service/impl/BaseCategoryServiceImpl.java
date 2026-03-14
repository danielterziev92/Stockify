package com.stockify.catalog.service.impl;

import com.stockify.catalog.constants.category.CategoryConstants;
import com.stockify.catalog.dto.category.CategoryDTO;
import com.stockify.catalog.dto.category.PatchCategoryDTO;
import com.stockify.catalog.mapper.category.BaseCategoryMapper;
import com.stockify.catalog.mapper.category.CategoryMVMapper;
import com.stockify.catalog.model.category.Category;
import com.stockify.catalog.model.category.CategoryMV;
import com.stockify.catalog.repository.category.CategoryMVRepository;
import com.stockify.catalog.repository.category.CategoryRepository;
import com.stockify.catalog.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
public abstract class BaseCategoryServiceImpl<
        T extends Category,
        R,
        REPO extends CategoryRepository<T>,
        MAPPER extends BaseCategoryMapper<T, R>,
        MV_MAPPER extends CategoryMVMapper<R>>
        implements CategoryService<R> {

    protected final REPO repository;
    protected final MAPPER mapper;
    protected final CategoryMVRepository mvRepository;
    protected final MV_MAPPER mvMapper;

    protected abstract String getDtype();

    @Override
    @Transactional(readOnly = true)
    public Page<R> getAll(@NonNull Pageable pageable) {
        return this.mvRepository.findAllByDtypeAndParentIdIsNull(this.getDtype(), pageable)
                .map(this.mvMapper::toResponse);
    }

    @Override
    public Page<R> getAllByActive(boolean active, @NonNull Pageable pageable) {
        return this.mvRepository.findAllByDtypeAndParentIdIsNullAndActive(this.getDtype(), active, pageable)
                .map(this.mvMapper::toResponse);
    }

    @Override
    public List<R> getByIdWithChildren(Long id) {
        List<CategoryMV> results = this.mvRepository.findCategoryWithChildren(this.getDtype(), id);

        if (results.isEmpty() || results.stream().noneMatch(c -> c.getId().equals(id)))
            throw new EntityNotFoundException(
                    CategoryConstants.CATEGORY_NOT_FOUND_BY_ID_MESSAGE.formatted(id)
            );

        return results.stream()
                .map(this.mvMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<R> search(String name, Boolean active) {
        return this.mvRepository.findAllByDtypeAndNameContainingIgnoreCaseAndActive(this.getDtype(), name, active)
                .stream()
                .map(this.mvMapper::toResponse)
                .toList();
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
    public R create(@NonNull CategoryDTO dto) {
        T category = this.mapper.toEntity(dto);

        category.setActive(dto.active() != null ? dto.active() : true);
        category.setDisplayOrder(dto.displayOrder() != null ? dto.displayOrder() : 0);

        if (dto.parentId() != null) this.checkAndSetParent(category, dto.parentId());

        return this.saveAndResponse(category);
    }

    @Override
    @Transactional
    public R update(@NonNull Long id, @NonNull PatchCategoryDTO dto) {
        T category = this.repository.findById(id)
                .orElseThrow(() -> this.categoryNotFound(id));

        if (dto.name() != null && dto.name().isBlank())
            throw new IllegalArgumentException("Category name cannot be blank");

        if (dto.name() != null) category.setName(dto.name());
        if (dto.active() != null) category.setActive(dto.active());
        if (dto.displayOrder() != null) category.setDisplayOrder(dto.displayOrder());
        if (dto.parentId() != null) this.checkAndSetParent(category, dto.parentId());

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

            if (this.repository.isDescendant(id, newParentId, this.getDtype()) != null)
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
