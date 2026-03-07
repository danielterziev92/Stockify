package com.stockify.catalog.repository;

import com.stockify.catalog.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findAllByActive(boolean active, Pageable pageable);

    List<Category> findAllByNameContainingIgnoreCaseAndActive(String name, boolean active);

    List<Category> findAllByParentIdAndActive(Long parentId, boolean active);

    Optional<Category> findByName(String name);

    boolean existsByName(String name);

    @Query(value = """
            WITH RECURSIVE descendants AS (
                SELECT id, parent_id
                FROM categories
                WHERE id = :categoryId
            
                UNION ALL
            
                SELECT c.id, c.parent_id
                FROM categories c
                INNER JOIN descendants d ON c.parent_id = d.id
            )
            SELECT id FROM descendants WHERE id = :targetId
            """, nativeQuery = true)
    boolean isDescendant(@Param("categoryId") Long categoryId, @Param("targetId") Long targetId);
}
