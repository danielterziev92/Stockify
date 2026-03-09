package com.stockify.catalog.domain.category.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("PRODUCT")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ProductCategory extends Category {
}
