package com.stockify.catalog.model.product;

import com.stockify.catalog.constants.product.ProductAttributeKeyConstants;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "product_attribute_keys",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_product_attribute_key_name", columnNames = "name")
        }
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAttributeKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @Column(nullable = false)
    private Long version;

    @Column(length = ProductAttributeKeyConstants.NAME_MAX_LENGTH, nullable = false)
    private String name;
}
