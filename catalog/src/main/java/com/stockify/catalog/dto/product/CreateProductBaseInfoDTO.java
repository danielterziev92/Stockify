package com.stockify.catalog.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stockify.catalog.constants.product.ProductBaseInfoConstants;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateProductBaseInfoDTO(
        @NotBlank(message = ProductBaseInfoConstants.NAME_NOT_BLANK_MESSAGE)
        @Size(max = ProductBaseInfoConstants.NAME_MAX_LENGTH, message = ProductBaseInfoConstants.NAME_SIZE_MESSAGE)
        String name,

        @Size(max = ProductBaseInfoConstants.ABBREVIATION_MAX_LENGTH, message = ProductBaseInfoConstants.ABBREVIATION_SIZE_MESSAGE)
        String abbreviation,

        @Size(max = ProductBaseInfoConstants.DESCRIPTION_MAX_LENGTH, message = ProductBaseInfoConstants.DESCRIPTION_SIZE_MESSAGE)
        String description,

        @NotNull(message = ProductBaseInfoConstants.BASE_PRICE_NOT_NULL_MESSAGE)
        @DecimalMin(value = "0.0", inclusive = false, message = ProductBaseInfoConstants.BASE_PRICE_MIN_VALUE_MESSAGE)
        @JsonProperty("base-price")
        BigDecimal basePrice,

        @NotNull(message = ProductBaseInfoConstants.TYPE_ID_NOT_NULL_MESSAGE)
        @JsonProperty("type-id")
        Long typeId,

        @NotNull(message = ProductBaseInfoConstants.MEASURE_ID_NOT_NULL_MESSAGE)
        @JsonProperty("measure-id")
        Long measureId,

        @NotNull(message = ProductBaseInfoConstants.CATEGORY_ID_NOT_NULL_MESSAGE)
        @JsonProperty("category-id")
        Long categoryId
) {
}
