package com.stockify.catalog.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stockify.catalog.constants.product.ProductBaseInfoConstants;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record PatchProductBaseInfoDTO(
        @Size(max = ProductBaseInfoConstants.NAME_MAX_LENGTH, message = ProductBaseInfoConstants.NAME_SIZE_MESSAGE)
        String name,

        @Size(max = ProductBaseInfoConstants.ABBREVIATION_MAX_LENGTH, message = ProductBaseInfoConstants.ABBREVIATION_SIZE_MESSAGE)
        String abbreviation,

        @Size(max = ProductBaseInfoConstants.DESCRIPTION_MAX_LENGTH, message = ProductBaseInfoConstants.DESCRIPTION_SIZE_MESSAGE)
        String description,

        @DecimalMin(value = "0.0", inclusive = false, message = ProductBaseInfoConstants.BASE_PRICE_MIN_VALUE_MESSAGE)
        @JsonProperty("base-price")
        BigDecimal basePrice,

        @JsonProperty("type-id")
        Long typeId,

        @JsonProperty("measure-id")
        Long measureId,

        @JsonProperty("category-id")
        Long categoryId,

        Boolean active
) {
}
