package com.stockify.catalog.application.category.usecase;

import com.stockify.catalog.application.category.dto.CategoryDTOs;
import com.stockify.catalog.application.category.mapper.PartnerCategoryMapper;
import com.stockify.catalog.domain.category.model.PartnerCategory;
import com.stockify.catalog.domain.category.service.CategoryDomainService;
import com.stockify.catalog.infrastructure.category.persistence.PartnerCategoryRepository;
import com.stockify.catalog.api.category.response.PartnerCategoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PartnerCategoryApplicationService
        extends CategoryApplicationService<PartnerCategory, PartnerCategoryResponse> {

    private final PartnerCategoryMapper mapper;

    public PartnerCategoryApplicationService(
            PartnerCategoryRepository repository,
            CategoryDomainService domainService,
            ApplicationEventPublisher eventPublisher,
            PartnerCategoryMapper mapper
    ) {
        super(repository, domainService, eventPublisher);
        this.mapper = mapper;
    }

    @Override
    protected PartnerCategory createEntity(CategoryDTOs.@NotNull CreateCategoryCommand command) {
        return PartnerCategory.builder()
                .name(command.name())
                .build();
    }

    @Override
    protected PartnerCategoryResponse toResponse(@NotNull PartnerCategory entity) {
        return this.mapper.toResponse(entity);
    }

    @Override
    protected String getDType() {
        return "";
    }
}
