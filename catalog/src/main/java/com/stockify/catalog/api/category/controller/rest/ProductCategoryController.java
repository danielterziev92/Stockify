package com.stockify.catalog.api.category.controller.rest;

import com.stockify.catalog.api.category.response.ProductCategoryResponse;
import com.stockify.catalog.application.category.usecase.ProductCategoryApplicationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products/categories")
public class ProductCategoryController
        extends BaseCategoryController<ProductCategoryResponse, ProductCategoryApplicationService> {

    public ProductCategoryController(ProductCategoryApplicationService service) {
        super(service);
    }
}
