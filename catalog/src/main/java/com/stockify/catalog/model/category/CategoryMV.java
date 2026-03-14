package com.stockify.catalog.model.category;

import com.stockify.catalog.dto.CategoryChildDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Immutable
@Table(name = "category_mv")
@Getter
@NoArgsConstructor
public class CategoryMV {

    @Id
    private Long id;

    private String name;

    @Column(name = "display_order")
    private Integer displayOrder;

    private Boolean active;

    private String dtype;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "children", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<CategoryChildDTO> children;
}
