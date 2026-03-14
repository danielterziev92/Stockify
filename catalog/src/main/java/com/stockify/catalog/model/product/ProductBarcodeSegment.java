package com.stockify.catalog.model.product;

import com.stockify.catalog.constants.product.ProductBarcodeSegmentConstants;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "product_barcode_segments",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_barcode_segment_order", columnNames = {"template_id", "order"})
        }
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductBarcodeSegment {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Version
    @Column(nullable = false)
    private Long version;

    @Column(length = ProductBarcodeSegmentConstants.NAME_MAX_LENGTH, nullable = false)
    private String name;

    @Column(length = ProductBarcodeSegmentConstants.SEGMENT_MAX_LENGTH, nullable = false)
    private String segment;

    @Column(name = "start_position", nullable = false)
    private Integer startPosition;

    @Column(nullable = false)
    private Integer length;

    @Column(nullable = false)
    private Integer decimals;

    @Column(nullable = false)
    private Integer order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "template_id", nullable = false)
    private ProductBarcodeTemplate template;
}
