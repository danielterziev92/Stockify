package com.stockify.catalog.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("PARTNER")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class PartnerCategory extends Category {
}
