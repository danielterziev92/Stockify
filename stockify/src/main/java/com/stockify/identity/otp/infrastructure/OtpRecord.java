package com.stockify.identity.otp.infrastructure;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

/**
 * Persistence model for an OTP row — the infrastructure counterpart of the
 * {@link com.stockify.identity.otp.domain.Otp} aggregate.
 *
 * <p>Holds only primitive/JDK types so Spring Data JDBC needs no custom converters
 * for the domain value objects ({@code OtpId}, {@code UserId}, {@code OtpType}).
 * Mapping between this record and the aggregate is the adapter's responsibility.
 *
 * @param id        the OTP identifier (primary key)
 * @param userId    the owning user's UUID
 * @param type      the {@code OtpType} name
 * @param code      the numeric code
 * @param expiresAt the expiry instant
 */
@Table(name = "otp")
record OtpRecord(
        @Id UUID id,
        UUID userId,
        String type,
        String code,
        Instant expiresAt
) {
}
