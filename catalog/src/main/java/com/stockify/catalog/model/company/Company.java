package com.stockify.catalog.model.company;

import com.stockify.catalog.constants.company.CompanyConstants;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "companies",
        indexes = {
                @Index(name = "idx_company_type", columnList = "type")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_company_name", columnNames = "name"),
                @UniqueConstraint(name = "uk_company_uic", columnNames = "uic"),
                @UniqueConstraint(name = "uk_company_vat", columnNames = "vat")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @Column(nullable = false)
    private Long version;

    @Column(length = CompanyConstants.Name.MAX_LENGTH, nullable = false)
    private String name;

    @Column(name = "name_en", length = CompanyConstants.NameEn.MAX_LENGTH)
    private String nameEn;

    @Column(length = CompanyConstants.Uic.MAX_LENGTH, nullable = false)
    private String uic;

    @Column(length = CompanyConstants.Vat.MAX_LENGTH)
    private String vat;

    @Column(length = CompanyConstants.Custodian.MAX_LENGTH, nullable = false)
    private String custodian;

    @Column(name = "custodian_en", length = CompanyConstants.CustodianEn.MAX_LENGTH)
    private String custodianEn;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private CompanyType type;

    @Column(nullable = false)
    private boolean active;
}
