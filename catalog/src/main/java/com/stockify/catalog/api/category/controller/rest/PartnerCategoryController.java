package com.stockify.catalog.api.category.controller.rest;

import com.stockify.catalog.api.category.response.PartnerCategoryResponse;
import com.stockify.catalog.application.category.usecase.PartnerCategoryApplicationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/partners/categories")
public class PartnerCategoryController
        extends BaseCategoryController<PartnerCategoryResponse, PartnerCategoryApplicationService> {

    public PartnerCategoryController(PartnerCategoryApplicationService service) {
        super(service);
    }
}
