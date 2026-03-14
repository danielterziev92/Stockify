package com.stockify.catalog.model.product;

import com.stockify.catalog.constants.product.ProductManufactureConstants;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "product_manufactures",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_product_manufacture_name", columnNames = "name")
        }
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductManufacture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @Column(nullable = false)
    private Long version;

    @Column(length = ProductManufactureConstants.NAME_MAX_LENGTH, nullable = false)
    private String name;
}
