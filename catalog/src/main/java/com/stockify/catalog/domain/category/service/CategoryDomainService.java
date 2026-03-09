package com.stockify.catalog.domain.category.service;

import com.stockify.catalog.domain.category.constants.CategoryConstants;
import com.stockify.catalog.domain.category.model.Category;
import com.stockify.catalog.domain.category.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryDomainService {

    public <T extends Category> void validateMove(
            @NotNull T category,
            Long newParentId,
            @NotNull CategoryRepository<T> repository,
            @NotNull String dtype
    ) {
        if (newParentId == null) return;

        if (newParentId.equals(category.getId()))
            throw new IllegalArgumentException(CategoryConstants.CATEGORY_OWN_PARENT_MESSAGE);

        if (repository.isDescendant(category.getId(), newParentId, dtype) != null)
            throw new IllegalArgumentException(CategoryConstants.CATEGORY_CIRCULAR_REFERENCE_MESSAGE);

        if (!repository.existsById(newParentId))
            throw new EntityNotFoundException(CategoryConstants.CATEGORY_NOT_FOUND_BY_ID_MESSAGE.formatted(newParentId));
    }

    public <T extends Category> void validateParent(
            @NotNull T category,
            Long parentId,
            @NotNull CategoryRepository<T> repository
    ) {
        if (parentId == null) return;

        if (!repository.existsById(parentId))
            throw new EntityNotFoundException(CategoryConstants.CATEGORY_NOT_FOUND_BY_ID_MESSAGE.formatted(parentId));
    }
}
