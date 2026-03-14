package com.stockify.catalog.repository.category;

import com.stockify.catalog.model.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface CategoryRepository<T extends Category> extends JpaRepository<T, Long> {
    Page<T> findAllByActive(boolean active, Pageable pageable);

    List<T> findAllByNameContainingIgnoreCaseAndActive(String name, boolean active);

    List<T> findAllByParentIdAndActive(Long parentId, boolean active);

    Optional<T> findByName(String name);

    boolean existsByName(String name);

    @Query(value = """
            WITH RECURSIVE descendants AS (
                SELECT id, parent_id
                FROM categories
                WHERE id = :categoryId
                AND dtype = :dtype
            
                UNION ALL
            
                SELECT c.id, c.parent_id
                FROM categories c
                INNER JOIN descendants d ON c.parent_id = d.id
                WHERE c.dtype = :dtype
            )
            SELECT id FROM descendants WHERE id = :targetId
            """, nativeQuery = true)
    Long isDescendant(
            @Param("categoryId") Long categoryId,
            @Param("targetId") Long targetId,
            @Param("dtype") String dtype
    );
}
