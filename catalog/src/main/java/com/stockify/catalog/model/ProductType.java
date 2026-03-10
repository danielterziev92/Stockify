package com.stockify.catalog.model;

import com.stockify.catalog.constants.ProductConstants;
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

    @Column(length = ProductConstants.TYPE_NAME_MAX_LENGTH, nullable = false)
    private String name;
}
