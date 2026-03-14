package com.stockify.catalog.model.category;

import com.stockify.catalog.constants.category.CategoryConstants;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@Table(
        name = "categories",
        indexes = {
                @Index(name = "idx_category_name_active_dtype", columnList = "name, active, dtype"),
                @Index(name = "idx_category_parent_active_dtype", columnList = "parent_id, active, dtype")
        }
)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING, columnDefinition = "VARCHAR(10)")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = CategoryConstants.NAME_MAX_LENGTH, nullable = false)
    private String name;

    @Version
    @Column(nullable = false)
    private Long version;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean active;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Category> children;
}
