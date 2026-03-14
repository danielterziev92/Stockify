package com.stockify.catalog.response.category;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductCategoryResponse(
        Long id,
        String name,
        boolean active,
        @JsonProperty("display-order") int displayOrder,
        @JsonProperty("partner-name") String partnerName,
        @JsonProperty("parent-id") Long parentId
) {
}
