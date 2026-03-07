package com.stockify.catalog.service.impl;

import com.stockify.catalog.mapper.PartnerCategoryMapper;
import com.stockify.catalog.model.PartnerCategory;
import com.stockify.catalog.repository.PartnerCategoryRepository;
import com.stockify.catalog.response.PartnerCategoryResponse;
import com.stockify.catalog.service.PartnerCategoryService;
import org.springframework.stereotype.Service;

@Service
public class PartnerCategoryServiceImpl extends BaseCategoryServiceImpl<
        PartnerCategory, PartnerCategoryResponse, PartnerCategoryRepository, PartnerCategoryMapper>
        implements PartnerCategoryService {

    public PartnerCategoryServiceImpl(PartnerCategoryRepository repository, PartnerCategoryMapper mapper) {
        super(repository, mapper);
    }
}
