package com.stockify.catalog.application.category.usecase;

import com.stockify.catalog.application.category.dto.CategoryDTOs;
import com.stockify.catalog.application.category.mapper.ProductCategoryMapper;
import com.stockify.catalog.domain.category.model.ProductCategory;
import com.stockify.catalog.domain.category.service.CategoryDomainService;
import com.stockify.catalog.infrastructure.category.persistence.ProductCategoryRepository;
import com.stockify.catalog.api.category.response.ProductCategoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductCategoryApplicationService
        extends CategoryApplicationService<ProductCategory, ProductCategoryResponse> {

    private final ProductCategoryMapper mapper;

    public ProductCategoryApplicationService(
            ProductCategoryRepository repository,
            CategoryDomainService domainService,
            ApplicationEventPublisher eventPublisher,
            ProductCategoryMapper mapper
    ) {
        super(repository, domainService, eventPublisher);
        this.mapper = mapper;
    }

    @Override
    protected ProductCategory createEntity(CategoryDTOs.@NotNull CreateCategoryCommand command) {
        return ProductCategory.builder()
                .name(command.name())
                .build();
    }

    @Override
    protected ProductCategoryResponse toResponse(@NotNull ProductCategory entity) {
        return this.mapper.toResponse(entity);
    }

    @Override
    protected String getDType() {
        return "PRODUCT";
    }
}
