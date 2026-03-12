package com.stockify.catalog.model;

import com.stockify.catalog.constants.ProductConstants;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(
        name = "products",
        indexes = {
                @Index(name = "idx_product_name_active", columnList = "name, active"),
                @Index(name = "idx_product_category", columnList = "category_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_product_abbreviation", columnNames = "abbreviation")
        }
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = ProductConstants.NAME_MAX_LENGTH, nullable = false)
    private String name;

    @Column(length = ProductConstants.ABBREVIATION_MAX_LENGTH)
    private String abbreviation;

    @Column(length = ProductConstants.DESCRIPTION_MAX_LENGTH)
    private String description;

    @Column(
            name = "base_price",
            nullable = false,
            precision = ProductConstants.BASE_PRICE_PRECISION,
            scale = ProductConstants.BASE_PRICE_SCALE
    )
    private BigDecimal basePrice;

    @Column(nullable = false)
    private Boolean active;

    @Version
    @Column(nullable = false)
    private Long version;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_type_id", nullable = false)
    private ProductType productType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private ProductCategory category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductImage> images;
}
