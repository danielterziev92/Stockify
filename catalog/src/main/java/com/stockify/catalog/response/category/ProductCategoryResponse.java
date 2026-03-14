package com.stockify.catalog.response.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stockify.catalog.dto.CategoryChildDTO;

import java.util.List;

public record ProductCategoryResponse(
        Long id,
        String name,
        boolean active,
        @JsonProperty("display-order") int displayOrder,
        @JsonProperty("parent-id") Long parentId,
        List<CategoryChildDTO> children
) {
}
