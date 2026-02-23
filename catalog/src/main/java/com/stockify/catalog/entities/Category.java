package com.stockify.catalog.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Audited
@Table(
        name = "categories",
        indexes = {
                @Index(name = "idx_category_name", columnList = "name"),
                @Index(name = "idx_category_parent_id", columnList = "parent_id")
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"parent", "children"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Category {
    public static final int NAME_MAX_LENGTH = 150;
    public static final String NAME_NOT_BLANK_MESSAGE = "Category name cannot be blank";
    public static final String NAME_SIZE_MESSAGE = "Category name cannot exceed {max} characters";

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @NotBlank(message = NAME_NOT_BLANK_MESSAGE)
    @Size(max = NAME_MAX_LENGTH, message = NAME_SIZE_MESSAGE)
    @Column(length = NAME_MAX_LENGTH, nullable = false)
    private String name;

    @Version
    @Column(nullable = false)
    private Long version;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(
            name = "fk_category_parent_id",
            value = ConstraintMode.CONSTRAINT,
            foreignKeyDefinition = "FOREIGN KEY (parent_id) REFERENCES categories(id) ON DELETE RESTRICT"
    ))
    private Category parent;

    @NotAudited
    @Builder.Default
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Category> children = new HashSet<>();
}

//CREATE_CATEGORIES