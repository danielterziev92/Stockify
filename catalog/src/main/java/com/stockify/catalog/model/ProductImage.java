package com.stockify.catalog.model;

import com.stockify.catalog.constants.ProductConstants;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "product_images",
        indexes = {
                @Index(name = "idx_image_product", columnList = "product_id")
        }
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = ProductConstants.URL_MAX_LENGTH, nullable = false)
    private String url;

    @Column(name = "display_order", nullable = false)
    private int displayOrder;

    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
