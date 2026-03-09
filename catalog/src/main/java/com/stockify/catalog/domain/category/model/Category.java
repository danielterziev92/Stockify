package com.stockify.catalog.domain.category.model;

import com.stockify.catalog.domain.category.constants.CategoryConstants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    public void moveTo(Category newParent) {
        this.parent = newParent;
    }

    public void moveToRoot() {
        this.parent = null;
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    public void rename(String newName) {
        if (newName == null || newName.isEmpty())
            throw new IllegalArgumentException(CategoryConstants.NAME_NOT_BLANK_MESSAGE);

        if (newName.length() > CategoryConstants.NAME_MAX_LENGTH)
            throw new IllegalArgumentException(CategoryConstants.NAME_SIZE_MESSAGE);

        this.name = newName;
    }

    public void reorder(int displayOrder) {
        if (displayOrder < 0)
            throw new IllegalArgumentException(CategoryConstants.DISPLAY_ORDER_NEGATIVE_MESSAGE);

        this.displayOrder = displayOrder;
    }

    public boolean isRoot() {
        return this.parent == null;
    }

    public boolean hasParent(Long parentId) {
        return this.parent != null && this.parent.getId().equals(parentId);
    }
}
