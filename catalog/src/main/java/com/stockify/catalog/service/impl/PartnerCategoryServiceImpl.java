package com.stockify.catalog.service.impl;

import com.stockify.catalog.mapper.category.PartnerCategoryMVMapper;
import com.stockify.catalog.mapper.category.PartnerCategoryMapper;
import com.stockify.catalog.model.category.PartnerCategory;
import com.stockify.catalog.repository.category.CategoryMVRepository;
import com.stockify.catalog.repository.category.PartnerCategoryRepository;
import com.stockify.catalog.response.category.PartnerCategoryResponse;
import com.stockify.catalog.service.PartnerCategoryService;
import org.springframework.stereotype.Service;

@Service
public class PartnerCategoryServiceImpl extends BaseCategoryServiceImpl<
        PartnerCategory,
        PartnerCategoryResponse,
        PartnerCategoryRepository,
        PartnerCategoryMapper,
        PartnerCategoryMVMapper>
        implements PartnerCategoryService {

    @Override
    protected String getDtype() {
        return "PARTNER";
    }

    public PartnerCategoryServiceImpl(
            PartnerCategoryRepository repository,
            PartnerCategoryMapper mapper,
            CategoryMVRepository mvRepository,
            PartnerCategoryMVMapper mvMapper
    ) {
        super(repository, mapper, mvRepository, mvMapper);
    }
}
