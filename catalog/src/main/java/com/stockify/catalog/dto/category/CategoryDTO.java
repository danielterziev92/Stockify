package com.stockify.catalog.dto.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stockify.catalog.constants.category.CategoryConstants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryDTO(
        @NotBlank(message = CategoryConstants.NAME_NOT_BLANK_MESSAGE)
        @Size(max = CategoryConstants.NAME_MAX_LENGTH, message = CategoryConstants.NAME_SIZE_MESSAGE)
        String name,

        Boolean active,

        @JsonProperty("display-order")
        @Min(value = CategoryConstants.DISPLAY_ORDER_MIN_VALUE, message = CategoryConstants.DISPLAY_ORDER_MIN_VALUE_MESSAGE)
        Integer displayOrder,

        @JsonProperty("parent-id")
        @Min(value = CategoryConstants.PARENT_ID_MIN_VALUE, message = CategoryConstants.PARENT_ID_MIN_VALUE_MESSAGE)
        Long parentId
) {
}
