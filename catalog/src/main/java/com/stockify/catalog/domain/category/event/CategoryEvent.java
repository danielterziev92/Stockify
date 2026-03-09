package com.stockify.catalog.domain.category.event;

public sealed interface CategoryEvent permits
        CategoryEvent.CategoryCreated,
        CategoryEvent.CategoryUpdated,
        CategoryEvent.CategoryMoved,
        CategoryEvent.CategoryDeleted {

    record CategoryCreated(Long categoryId, String name, String dtype) implements CategoryEvent {
    }

    record CategoryUpdated(Long categoryId, String dtype) implements CategoryEvent {
    }

    record CategoryMoved(Long categoryId, Long oldParentId, Long newParentId, String dtype) implements CategoryEvent {
    }

    record CategoryDeleted(Long categoryId, String dtype) implements CategoryEvent {
    }
}
