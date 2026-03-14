package com.stockify.catalog.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stockify.catalog.constants.product.ProductAdditionalInfoConstants;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateProductAdditionalInfoDTO(
        @DecimalMin(value = "0.0", message = ProductAdditionalInfoConstants.WEIGHT_MIN_VALUE_MESSAGE)
        BigDecimal weight,

        @DecimalMin(value = "0.0", message = ProductAdditionalInfoConstants.HEIGHT_MIN_VALUE_MESSAGE)
        BigDecimal height,

        @DecimalMin(value = "0.0", message = ProductAdditionalInfoConstants.DEPTH_MIN_VALUE_MESSAGE)
        BigDecimal depth,

        @Size(max = ProductAdditionalInfoConstants.DIMENSION_TYPE_MAX_LENGTH, message = ProductAdditionalInfoConstants.DIMENSION_TYPE_SIZE_MESSAGE)
        @JsonProperty("dimension-type")
        String dimensionType,

        @JsonProperty("weight-measurement-id")
        Long weightMeasurementId,

        @JsonProperty("manufacture-id")
        Long manufactureId,

        @JsonProperty("country-of-origin-id")
        Long countryOfOriginId,

        @NotNull(message = ProductAdditionalInfoConstants.PRODUCT_ID_NOT_NULL_MESSAGE)
        @JsonProperty("product-id")
        Long productId
) {
}
