package com.stockify.catalog.product.application.dto;

import java.util.UUID;

public record AttributeValueDto(UUID id, String value, String abbreviation) {
}
