package com.stockify.catalog.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stockify.catalog.constants.product.ProductBarcodeConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateProductBarcodeDTO(
        @NotBlank(message = ProductBarcodeConstants.VALUE_NOT_BLANK_MESSAGE)
        @Size(max = ProductBarcodeConstants.VALUE_MAX_LENGTH, message = ProductBarcodeConstants.VALUE_SIZE_MESSAGE)
        String value,

        @NotBlank(message = ProductBarcodeConstants.TYPE_NOT_BLANK_MESSAGE)
        @Size(max = ProductBarcodeConstants.TYPE_MAX_LENGTH, message = ProductBarcodeConstants.TYPE_SIZE_MESSAGE)
        String type,

        @NotNull(message = ProductBarcodeConstants.VARIANT_ID_NOT_NULL_MESSAGE)
        @JsonProperty("variant-id")
        Long variantId
) {
}
