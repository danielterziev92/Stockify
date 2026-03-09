package com.stockify.catalog.application.category.usecase;

import com.stockify.catalog.application.category.dto.CategoryDTOs;
import com.stockify.catalog.domain.category.constants.CategoryConstants;
import com.stockify.catalog.domain.category.event.CategoryEvent;
import com.stockify.catalog.domain.category.model.Category;
import com.stockify.catalog.domain.category.repository.CategoryRepository;
import com.stockify.catalog.domain.category.service.CategoryDomainService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
public abstract class CategoryApplicationService<T extends Category, R> {

    protected final CategoryRepository<T> repository;
    protected final CategoryDomainService domainService;
    protected final ApplicationEventPublisher eventPublisher;

    protected abstract T createEntity(CategoryDTOs.CreateCategoryCommand command);

    protected abstract R toResponse(T entity);

    protected abstract String getDType();

    @Transactional(readOnly = true)
    public Page<R> getAll(Pageable pageable) {
        return this.repository.findAll(pageable).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<R> getAllByActive(boolean active, Pageable pageable) {
        return this.repository.findAllByActive(active, pageable).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public List<R> search(String name, boolean active) {
        return this.repository.findAllByNameContainingIgnoreCaseAndActive(name, active)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public R getById(Long id) {
        return this.repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> this.notFound(id));
    }

    @Transactional
    public R create(CategoryDTOs.@NotNull CreateCategoryCommand command) {
        T category = createEntity(command);

        category.setActive(command.active() != null ? command.active() : CategoryConstants.ACTIVE_DEFAULT_VALUE);
        category.setDisplayOrder(command.displayOrder() != null ? command.displayOrder() : CategoryConstants.DISPLAY_ORDER_DEFAULT_VALUE);

        if (command.parentId() != null) {
            this.domainService.validateParent(category, command.parentId(), this.repository);
            category.moveTo(this.repository.getReferenceById(command.parentId()));
        }

        T saved = this.repository.save(category);

        this.eventPublisher.publishEvent(new CategoryEvent.CategoryCreated(saved.getId(), saved.getName(), getDType()));

        return this.toResponse(saved);
    }

    @Transactional
    public R update(@NotNull Long id, CategoryDTOs.@NotNull UpdateCategoryCommand command) {
        T category = this.repository.findById(id).orElseThrow(() -> this.notFound(id));

        if (command.name() != null) category.rename(command.name());
        if (command.active() != null && command.active()) category.activate();
        if (command.active() != null && !command.active()) category.deactivate();
        if (command.displayOrder() != null) category.reorder(command.displayOrder());

        if (command.parentId() != null) {
            this.domainService.validateParent(category, command.parentId(), this.repository);
            category.moveTo(this.repository.getReferenceById(command.parentId()));
        }

        T saved = this.repository.save(category);

        this.eventPublisher.publishEvent(new CategoryEvent.CategoryUpdated(saved.getId(), this.getDType()));

        return this.toResponse(saved);
    }

    @Transactional
    public R move(@NotNull Long id, Long newParentId) {
        T category = this.repository.findById(id).orElseThrow(() -> this.notFound(id));

        Long oldParentId = category.getParent() != null ? category.getParent().getId() : null;

        this.domainService.validateMove(category, newParentId, this.repository, this.getDType());

        if (newParentId != null) {
            category.moveTo(repository.getReferenceById(newParentId));
        } else {
            category.moveToRoot();
        }

        T saved = this.repository.save(category);

        this.eventPublisher.publishEvent(
                new CategoryEvent.CategoryMoved(saved.getId(), oldParentId, newParentId, this.getDType())
        );

        return this.toResponse(saved);
    }


    @Transactional
    public void delete(@NotNull Long id) {
        T category = this.repository.findById(id).orElseThrow(() -> this.notFound(id));
        this.repository.delete(category);
        this.eventPublisher.publishEvent(new CategoryEvent.CategoryDeleted(id, this.getDType()));
    }

    @Contract("_ -> new")
    private @NonNull EntityNotFoundException notFound(@NotNull Long id) {
        return new EntityNotFoundException(CategoryConstants.CATEGORY_NOT_FOUND_BY_ID_MESSAGE.formatted(id));
    }
}
