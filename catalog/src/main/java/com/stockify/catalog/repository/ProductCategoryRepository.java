package com.stockify.catalog.repository;

import com.stockify.catalog.model.ProductCategory;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends CategoryRepository<ProductCategory> { }
