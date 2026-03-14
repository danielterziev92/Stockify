package com.stockify.catalog.model.product;

import com.stockify.catalog.constants.product.ProductAttributeValueConstants;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "product_attribute_values",
        indexes = {
                @Index(name = "idx_product_attribute_values_value", columnList = "value"),
                @Index(name = "idx_product_attribute_values_key", columnList = "key_id")
        }
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAttributeValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @Column(nullable = false)
    private Long version;

    @Column(length = ProductAttributeValueConstants.VALUE_MAX_LENGTH, nullable = false)
    private String value;

    @Column(length = ProductAttributeValueConstants.ABBREVIATION_MAX_LENGTH, nullable = false)
    private String abbreviation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "key_id", nullable = false)
    private ProductAttributeKey key;
}
