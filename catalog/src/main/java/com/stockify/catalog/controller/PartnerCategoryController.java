package com.stockify.catalog.controller;

import com.stockify.catalog.response.PartnerCategoryResponse;
import com.stockify.catalog.service.PartnerCategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/partners/categories")
public class PartnerCategoryController extends BaseCategoryController<PartnerCategoryResponse, PartnerCategoryService> {
    public PartnerCategoryController(PartnerCategoryService service) {
        super(service);
    }
}
