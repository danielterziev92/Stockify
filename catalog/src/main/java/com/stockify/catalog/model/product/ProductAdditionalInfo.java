package com.stockify.catalog.model.product;

import com.stockify.catalog.constants.product.ProductAdditionalInfoConstants;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "product_additional_info",
        indexes = {
                @Index(name = "idx_product_additional_info_weight_measurement", columnList = "weight_measurement_id"),
                @Index(name = "idx_product_additional_info_manufacture", columnList = "product_manufacture_id"),
                @Index(name = "idx_product_additional_info_country_of_origin", columnList = "country_of_origin_id"),
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_product_additional_info_product", columnNames = "product_id"),
        }
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAdditionalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @Column(nullable = false)
    private Long version;

    @Column(
            precision = ProductAdditionalInfoConstants.WIDTH_PRECISION,
            scale = ProductAdditionalInfoConstants.WIDTH_SCALE
    )
    private BigDecimal weight;

    @Column(
            precision = ProductAdditionalInfoConstants.HEIGHT_PRECISION,
            scale = ProductAdditionalInfoConstants.HEIGHT_SCALE
    )
    private BigDecimal height;

    @Column(
            precision = ProductAdditionalInfoConstants.DEPTH_PRECISION,
            scale = ProductAdditionalInfoConstants.DEPTH_SCALE
    )
    private BigDecimal depth;

    @Column(length = ProductAdditionalInfoConstants.DIMENSION_TYPE_MAX_LENGTH)
    private String dimension_type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weight_measurement_id")
    private ProductWeightMeasurement weightMeasurement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_manufacture_id")
    private ProductManufacture manufacture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_of_origin_id")
    private ProductCountryOfOrigin countryOfOrigin;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductBaseInfo product;
}
