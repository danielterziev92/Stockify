package com.stockify.catalog.controller.graphql;

import com.stockify.catalog.dto.CategoryDTO;
import com.stockify.catalog.dto.PatchCategoryDTO;
import com.stockify.catalog.response.PageMetaResponse;
import com.stockify.catalog.response.PageResponse;
import com.stockify.catalog.response.ProductCategoryResponse;
import com.stockify.catalog.service.ProductCategoryService;
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
public class ProductCategoryGraphQLController {

    private final ProductCategoryService service;

    @QueryMapping
    public PageResponse<ProductCategoryResponse> productCategories(
            @Argument Integer page,
            @Argument Integer size,
            @Argument Boolean active
    ) {
        Pageable pageable = PageRequest.of(
                page != null ? page - 1 : 0,
                size != null ? size : 10,
                Sort.by("displayOrder", "id")
        );

        Page<ProductCategoryResponse> result = active != null
                ? this.service.getAllByActive(active, pageable)
                : this.service.getAll(pageable);

        return new PageResponse<>(PageMetaResponse.from(result), result.getContent());
    }

    @QueryMapping
    public ProductCategoryResponse productCategory(@Argument Long id) {
        return this.service.getById(id);
    }

    @QueryMapping
    public List<ProductCategoryResponse> searchProductCategories(
            @Argument String name,
            @Argument Boolean active
    ) {
        return this.service.search(name, active != null ? active : true);
    }

    @MutationMapping
    public ProductCategoryResponse createProductCategory(@Argument CategoryDTO input) {
        return this.service.create(input);
    }

    @MutationMapping
    public ProductCategoryResponse updateProductCategory(
            @Argument Long id,
            @Argument PatchCategoryDTO input
    ) {
        return this.service.update(id, input);
    }

    @MutationMapping
    public ProductCategoryResponse moveProductCategory(
            @Argument Long id,
            @Argument Long parentId
    ) {
        return this.service.move(id, parentId);
    }

    @MutationMapping
    public void deleteProductCategory(@Argument Long id) {
        this.service.delete(id);
    }
}
