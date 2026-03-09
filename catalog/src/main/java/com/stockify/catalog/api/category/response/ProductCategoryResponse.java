package com.stockify.catalog.api.category.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductCategoryResponse(
        Long id,
        String name,
        boolean active,
        @JsonProperty("display-order") int displayOrder,
        @JsonProperty("parent-id") Long parentId
) {
}
