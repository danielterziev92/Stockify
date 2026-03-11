package com.stockify.catalog.openapi;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(name = "CategoryPageable")
public class CategoryPageableAsQueryParam {
    @Schema(
            description = "Page number (1-indexed)",
            defaultValue = "1",
            minimum = "1"
    )
    private int page;

    @Schema(
            description = "Number of items per page",
            defaultValue = "10",
            minimum = "1"
    )
    private int size;

    @Schema(
            description = "Sorting criteria in format: field,direction. " +
                    "Default: displayOrder,asc",
            allowableValues = {
                    "displayOrder,asc", "displayOrder,desc",
                    "id,asc", "id,desc",
                    "name,asc", "name,desc",
                    "active,asc", "active,desc"
            },
            defaultValue = "displayOrder,asc",
            example = "displayOrder,asc"
    )
    private String[] sort;
}
