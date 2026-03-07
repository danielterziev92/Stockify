package com.stockify.catalog.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CategoryResponse(
        Long id,
        String name,
        boolean active,
        @JsonProperty("display-order") int displayOrder,
        @JsonProperty("parent-id") Long parentId
) {
}
