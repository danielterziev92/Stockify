package com.stockify.catalog.repository;

import java.util.List;

public record PageResponse<T>(
        PageMetaResponse meta,
        List<T> data
) {
}
