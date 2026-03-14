package com.stockify.catalog.repository.category;

import com.stockify.catalog.model.category.CategoryMV;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMVRepository extends JpaRepository<CategoryMV, Long> {

    Page<CategoryMV> findAllByDtypeAndParentIdIsNull(String dtype, Pageable pageable);

    Page<CategoryMV> findAllByDtypeAndParentIdIsNullAndActive(String dtype, boolean active, Pageable pageable);

    @Query("""
            SELECT c
            FROM CategoryMV AS c
            WHERE c.dtype = :dtype
            AND (c.id = :id OR c.parentId = :id)
            ORDER BY c.parentId NULLS FIRST, c.displayOrder ASC
            """)
    List<CategoryMV> findCategoryWithChildren(@Param("dtype") String dtype, @Param("id") Long id);

    List<CategoryMV> findAllByDtypeAndNameContainingIgnoreCaseAndActive(String dtype, String name, boolean active);
}
