package com.stockify.catalog.api.category.response;

import java.util.List;

public record PageResponse<T>(
        PageMetaResponse meta,
        List<T> data
) {
}
