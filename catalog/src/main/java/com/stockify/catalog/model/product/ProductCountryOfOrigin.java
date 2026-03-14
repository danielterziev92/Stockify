package com.stockify.catalog.model.product;

import com.stockify.catalog.constants.product.ProductCountryOfOriginConstants;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "product_country_of_origins",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_product_country_of_origin_name", columnNames = "name")
        }
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCountryOfOrigin {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Version
    @Column(nullable = false)
    private Long version;

    @Column(length = ProductCountryOfOriginConstants.NAME_MAX_LENGTH, nullable = false)
    private String name;
}
