package com.stockify.catalog.service.impl;

import com.stockify.catalog.mapper.ProductCategoryMapper;
import com.stockify.catalog.model.ProductCategory;
import com.stockify.catalog.repository.ProductCategoryRepository;
import com.stockify.catalog.response.ProductCategoryResponse;
import com.stockify.catalog.service.ProductCategoryService;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryServiceImpl extends BaseCategoryServiceImpl<
        ProductCategory, ProductCategoryResponse, ProductCategoryRepository, ProductCategoryMapper
        > implements ProductCategoryService {

    public ProductCategoryServiceImpl(ProductCategoryRepository repository, ProductCategoryMapper mapper) {
        super(repository, mapper);
    }
}
