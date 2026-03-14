package com.stockify.catalog.model.product;

import com.stockify.catalog.constants.product.ProductWeightMeasurementConstants;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "product_weight_measurements",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_product_weight_measurement_unit", columnNames = "unit")
        }
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductWeightMeasurement {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Version
    @Column(nullable = false)
    private Long version;

    @Column(length = ProductWeightMeasurementConstants.UNIT_MAX_LENGTH, nullable = false)
    private String unit;

    @Column(nullable = false)
    private Integer precision;

    @Column(nullable = false)
    private Integer scale;
}
