package com.stockify.catalog.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stockify.catalog.constants.product.ProductImageConstants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PatchProductImageDTO(
        @Size(min = ProductImageConstants.URL_MAX_LENGTH, message = ProductImageConstants.URL_SIZE_MESSAGE)
        String url,

        @Min(value = ProductImageConstants.DISPLAY_ORDER_MIN_VALUE, message = ProductImageConstants.DISPLAY_ORDER_MIN_VALUE_MESSAGE)
        @JsonProperty("display-order")
        Integer displayOrder,

        @NotNull(message = ProductImageConstants.IS_PRIMARY_NOT_NULL_MESSAGE)
        @JsonProperty("is-primary")
        Boolean isPrimary
) {
}
