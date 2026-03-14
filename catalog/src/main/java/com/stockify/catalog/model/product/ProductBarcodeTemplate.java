package com.stockify.catalog.model.product;

import com.stockify.catalog.constants.product.ProductBarcodeTemplateConstants;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(
        name = "product_barcode_templates",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_product_barcode_template_prefix", columnNames = "prefix")
        }
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductBarcodeTemplate {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Version
    @Column(nullable = false)
    private Long version;

    @Column(length = ProductBarcodeTemplateConstants.NAME_MAX_LENGTH, nullable = false)
    private String name;

    @Column(length = ProductBarcodeTemplateConstants.PREFIX_MAX_LENGTH, nullable = false)
    private String prefix;

    @Column(nullable = false)
    private Integer length;

    @OneToMany(mappedBy = "template", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductBarcodeSegment> segments;
}
