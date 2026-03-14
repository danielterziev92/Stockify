package com.stockify.catalog.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stockify.catalog.constants.product.ProductVariantAttributeConstants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateProductVariantAttributeDTO(
        @NotNull(message = ProductVariantAttributeConstants.VARIANT_ID_NOT_NULL_MESSAGE)
        @JsonProperty("variant-id")
        Long variantId,

        @NotNull(message = ProductVariantAttributeConstants.ATTRIBUTE_VALUE_ID_NOT_NULL_MESSAGE)
        @JsonProperty("attribute-value-id")
        Long attributeValueId,

        @NotNull(message = ProductVariantAttributeConstants.ORDER_NOT_NULL_MESSAGE)
        @Min(value = 0, message = ProductVariantAttributeConstants.ORDER_MIN_VALUE_MESSAGE)
        Integer order
) {
}
