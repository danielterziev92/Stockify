package com.stockify.catalog.service.impl;

import com.stockify.catalog.mapper.category.ProductCategoryMVMapper;
import com.stockify.catalog.mapper.category.ProductCategoryMapper;
import com.stockify.catalog.model.category.ProductCategory;
import com.stockify.catalog.repository.category.CategoryMVRepository;
import com.stockify.catalog.repository.category.ProductCategoryRepository;
import com.stockify.catalog.response.category.ProductCategoryResponse;
import com.stockify.catalog.service.ProductCategoryService;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryServiceImpl extends BaseCategoryServiceImpl<
        ProductCategory,
        ProductCategoryResponse,
        ProductCategoryRepository,
        ProductCategoryMapper,
        ProductCategoryMVMapper
        > implements ProductCategoryService {

    @Override
    protected String getDtype() {
        return "PRODUCT";
    }

    public ProductCategoryServiceImpl(
            ProductCategoryRepository repository,
            ProductCategoryMapper mapper,
            CategoryMVRepository mvRepository,
            ProductCategoryMVMapper mvMapper
    ) {
        super(repository, mapper, mvRepository, mvMapper);
    }
}
