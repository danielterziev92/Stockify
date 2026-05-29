package com.stockify.identity.otp.infrastructure;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JDBC store for {@link OtpRecord}. Internal to the infrastructure
 * package — the rest of the application depends on
 * {@link com.stockify.identity.otp.domain.OtpRepository} instead.
 */
interface OtpJdbcRepository extends CrudRepository<OtpRecord, UUID> {

    /**
     * Finds the OTP row for the given user and type.
     *
     * @param userId the owning user's UUID
     * @param type   the {@code OtpType} name
     * @return the matching record, if any
     */
    Optional<OtpRecord> findByUserIdAndType(UUID userId, String type);
}
