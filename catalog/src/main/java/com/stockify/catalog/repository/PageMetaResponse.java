package com.stockify.catalog.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;

public record PageMetaResponse(
        @JsonProperty("current-page") int currentPage,
        @JsonProperty("last-page") int lastPage,
        @JsonProperty("items-per-page") int itemsPerPage,
        @JsonProperty("total-items") long totalItems
) {
    @Contract("_ -> new")
    public static @NonNull PageMetaResponse from(@NonNull Page<?> page) {
        return new PageMetaResponse(
                page.getNumber() + 1,
                page.getTotalPages(),
                page.getSize(),
                page.getTotalElements()
        );
    }
}
