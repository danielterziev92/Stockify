package com.stockify.catalog.model.product;

import com.stockify.catalog.constants.product.ProductBarcodeConstants;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "product_barcodes",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_product_barcode_value", columnNames = "value")
        }
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductBarcode {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Version
    @Column(nullable = false)
    private Long version;

    @Column(length = ProductBarcodeConstants.VALUE_MAX_LENGTH, nullable = false)
    private String value;

    @Column(length = ProductBarcodeConstants.TYPE_MAX_LENGTH, nullable = false)
    private String type;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "variant_id", nullable = false)
    private ProductVariant variant;
}
