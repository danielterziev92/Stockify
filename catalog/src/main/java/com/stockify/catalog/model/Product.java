package com.stockify.catalog.model;

import com.stockify.catalog.constants.ProductConstants;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "products",
        indexes = {
                @Index(name = "idx_product_name", columnList = "name"),
                @Index(name = "idx_product_name_active", columnList = "name, active"),
                @Index(name = "idx_product_type", columnList = "product_type_id"),
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

    @Column(precision = ProductConstants.BASE_PRICE_PRECISION, scale = ProductConstants.BASE_PRICE_SCALE, nullable = false)
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
}
