package com.stockify.catalog.repository.category;

import com.stockify.catalog.model.category.ProductCategory;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends CategoryRepository<ProductCategory> { }
