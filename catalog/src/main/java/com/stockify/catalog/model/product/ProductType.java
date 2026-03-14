package com.stockify.catalog.model.product;

import com.stockify.catalog.constants.product.ProductTypeConstants;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "product_types",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_product_type_name", columnNames = "name")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @Column(nullable = false)
    private Long version;

    @Column(length = ProductTypeConstants.NAME_MAX_LENGTH, nullable = false)
    private String name;
}
