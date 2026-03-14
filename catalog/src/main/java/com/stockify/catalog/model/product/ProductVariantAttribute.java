package com.stockify.catalog.model.product;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "product_variant_attributes",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_variant_attribute_value", columnNames = {"variant_id", "attribute_value_id"}),
                @UniqueConstraint(name = "uk_variant_attribute_order", columnNames = {"variant_id", "order"})
        }
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariantAttribute {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Version
    @Column(nullable = false)
    private Long version;

    @Column(name = "order", nullable = false)
    private Integer order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "variant_id", nullable = false)
    private ProductVariant variant;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "attribute_value_id", nullable = false)
    private ProductAttributeValue attributeValue;
}
