package com.stockify.catalog.controller.rest.category;

import com.stockify.catalog.response.category.ProductCategoryResponse;
import com.stockify.catalog.service.ProductCategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products/categories")
public class ProductCategoryController extends BaseCategoryController<ProductCategoryResponse, ProductCategoryService> {
    public ProductCategoryController(ProductCategoryService service) {
        super(service);
    }
}
