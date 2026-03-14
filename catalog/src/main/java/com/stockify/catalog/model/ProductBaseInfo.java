package com.stockify.catalog.model;

import com.stockify.catalog.constants.product.ProductConstants;
import com.stockify.catalog.model.category.ProductCategory;
import com.stockify.catalog.model.product.ProductMeasures;
import com.stockify.catalog.model.product.ProductType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(
        name = "product_base_infos",
        indexes = {
                @Index(name = "idx_product_name_active", columnList = "name, active"),
                @Index(name = "idx_product_type", columnList = "type_id"),
                @Index(name = "idx_product_measure", columnList = "measure_id"),
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
public class ProductBaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @Column(nullable = false)
    private Long version;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "type_id", nullable = false)
    private ProductType type;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "measure_id", nullable = false)
    private ProductMeasures measure;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private ProductCategory category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductImage> images;
}
