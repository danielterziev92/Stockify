package com.stockify.catalog.api.category.controller.graphql;

import com.stockify.catalog.api.category.response.PageMetaResponse;
import com.stockify.catalog.api.category.response.PageResponse;
import com.stockify.catalog.api.category.response.PartnerCategoryResponse;
import com.stockify.catalog.application.category.dto.CategoryDTOs;
import com.stockify.catalog.application.category.usecase.PartnerCategoryApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class PartnerCategoryGraphQLController {

    private final PartnerCategoryApplicationService service;

    @QueryMapping
    public PageResponse<PartnerCategoryResponse> partnerCategories(
            @Argument Integer page,
            @Argument Integer size,
            @Argument Boolean active
    ) {
        Pageable pageable = PageRequest.of(
                page != null ? page - 1 : 0,
                size != null ? size : 10,
                Sort.by("displayOrder", "id")
        );

        Page<PartnerCategoryResponse> result = active != null
                ? this.service.getAllByActive(active, pageable)
                : this.service.getAll(pageable);

        return new PageResponse<>(PageMetaResponse.from(result), result.getContent());
    }

    @QueryMapping
    public PartnerCategoryResponse partnerCategory(@Argument Long id) {
        return this.service.getById(id);
    }

    @QueryMapping
    public List<PartnerCategoryResponse> searchPartnerCategories(
            @Argument String name,
            @Argument Boolean active
    ) {
        return this.service.search(name, active != null ? active : true);
    }

    @MutationMapping
    public PartnerCategoryResponse createPartnerCategory(@Argument CategoryDTOs.CreateCategoryCommand input) {
        return this.service.create(input);
    }

    @MutationMapping
    public PartnerCategoryResponse updatePartnerCategory(
            @Argument Long id,
            @Argument CategoryDTOs.UpdateCategoryCommand input
    ) {
        return this.service.update(id, input);
    }

    @MutationMapping
    public PartnerCategoryResponse movePartnerCategory(
            @Argument Long id,
            @Argument Long parentId
    ) {
        return this.service.move(id, parentId);
    }

    @MutationMapping
    public void deletePartnerCategory(@Argument Long id) {
        this.service.delete(id);
    }
}
