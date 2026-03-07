package com.stockify.catalog.model;

import com.stockify.catalog.constants.CategoryConstants;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "categories",
        indexes = {
                @Index(name = "idx_category_name_active", columnList = "name, active"),
                @Index(name = "idx_category_parent_active", columnList = "parent_id, active")
        }
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

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
    @Builder.Default
    private Set<Category> children = new HashSet<>();
}
