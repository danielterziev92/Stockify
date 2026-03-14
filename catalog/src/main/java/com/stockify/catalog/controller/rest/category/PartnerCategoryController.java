package com.stockify.catalog.controller.rest.category;

import com.stockify.catalog.response.category.PartnerCategoryResponse;
import com.stockify.catalog.service.PartnerCategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/partners/categories")
public class PartnerCategoryController extends BaseCategoryController<PartnerCategoryResponse, PartnerCategoryService> {
    public PartnerCategoryController(PartnerCategoryService service) {
        super(service);
    }
}
