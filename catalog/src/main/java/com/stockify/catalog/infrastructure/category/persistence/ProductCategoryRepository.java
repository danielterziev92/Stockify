package com.stockify.catalog.infrastructure.category.persistence;

import com.stockify.catalog.domain.category.model.ProductCategory;
import com.stockify.catalog.domain.category.repository.CategoryRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends CategoryRepository<ProductCategory> { }
