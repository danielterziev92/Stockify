package com.stockify.catalog.controller;

import com.stockify.catalog.response.ProductCategoryResponse;
import com.stockify.catalog.service.ProductCategoryService;

public class ProductCategoryController extends BaseCategoryController<ProductCategoryResponse, ProductCategoryService> {
    public ProductCategoryController(ProductCategoryService service) {
        super(service);
    }
}
