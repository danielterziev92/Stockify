package com.stockify.catalog.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stockify.catalog.constants.product.ProductVariantConstants;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record PatchProductVariantDTO(
        @Size(max = ProductVariantConstants.SKU_MAX_LENGTH, message = ProductVariantConstants.SKU_SIZE_MESSAGE)
        String sku,

        @DecimalMin(value = "0.0", message = ProductVariantConstants.ADDITIONAL_PRICE_MIN_VALUE_MESSAGE)
        @JsonProperty("additional-price")
        BigDecimal additionalPrice,

        @JsonProperty("image-id")
        Long imageId,

        Boolean active,

        @JsonProperty("attribute-value-ids")
        List<Long> attributeValueIds
) {
}
