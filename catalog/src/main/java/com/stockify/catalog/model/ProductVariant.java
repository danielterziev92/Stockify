package com.stockify.catalog.model;

import com.stockify.catalog.constants.ProductVariantConstants;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "product_variants",
        indexes = {
                @Index(name = "idx_variant_product_active", columnList = "product_id, active"),
                @Index(name = "idx_variant_sku_active", columnList = "sku, active"),
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_variant_sku", columnNames = "sku")
        }
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = ProductVariantConstants.SKU_MAX_LENGTH)
    private String sku;

    @Column(
            name = "additional_price",
            nullable = false,
            precision = ProductVariantConstants.ADDITIONAL_PRICE_PRECISION,
            scale = ProductVariantConstants.ADDITIONAL_PRICE_SCALE
    )
    private BigDecimal additionalPrice;

    @Column(nullable = false)
    private Boolean active;

    @Version
    @Column(nullable = false)
    private Long version;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private ProductImage image;
}
