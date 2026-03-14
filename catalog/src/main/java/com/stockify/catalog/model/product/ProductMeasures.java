package com.stockify.catalog.model.product;

import com.stockify.catalog.constants.product.ProductMeasuresConstants;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "product_measures",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_product_measures_unit", columnNames = "unit")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductMeasures {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @Column(nullable = false)
    private Long version;

    @Column(length = ProductMeasuresConstants.UNIT_MAX_LENGTH, nullable = false)
    private String unit;

    @Column(nullable = false)
    private Integer precision;

    @Column(nullable = false)
    private Integer scale;
}
