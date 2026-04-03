package com.stockify.catalog.product.application.dto;

import java.util.List;
import java.util.UUID;

public record AttributeKeyDto(UUID uuid, String name, List<AttributeValueDto> values) {
}
