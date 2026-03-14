package com.stockify.catalog.model.category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

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

    @Column(name = "parent_name")
    private String parentName;

    @Column(name = "parent_id")
    private Long parentId;
}
