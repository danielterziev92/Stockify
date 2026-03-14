package com.stockify.catalog.model.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

@Entity
@Immutable
@Table(name = "product_mv")
@Getter
@NoArgsConstructor
public class ProductMV {

    @Id
    private Long id;

    private String sku;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "final_price")
    private BigDecimal finalPrice;

    private Boolean active;

    private String url;

    @Column(name = "measure_unit")
    private String measureUnit;

    @Column(name = "measure_precision")
    private Integer measurePrecision;

    @Column(name = "measure_scale")
    private Integer measureScale;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "type_id")
    private Long typeId;

    @Column(name = "type_name")
    private String typeName;
}
