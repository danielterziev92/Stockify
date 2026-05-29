package com.stockify.identity.otp.domain;

import com.stockify.shared.vo.UserId;
import org.jmolecules.ddd.types.Repository;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

/**
 * Repository port for the {@link Otp} aggregate root.
 *
 * <p>The contract is deliberately small: an OTP is always located by its owner and
 * purpose — never by code alone, which would expose an enumeration surface over the
 * narrow numeric code space.
 */
public interface OtpRepository extends Repository<Otp, OtpId> {

    /**
     * Looks up the single {@link Otp} for the given user and purpose.
     *
     * @param userId the identifier of the owning user; must not be {@code null}
     * @param type   the OTP type to search for; must not be {@code null}
     * @return an {@link Optional} containing the matching OTP, or empty if none exists
     */
    Optional<Otp> findByUserIdAndType(@NonNull UserId userId, @NonNull OtpType type);

    /**
     * Persists an {@link Otp}, inserting a new record or updating the existing one
     * with the same identifier.
     *
     * @param otp the OTP to persist; must not be {@code null}
     */
    void save(@NonNull Otp otp);

    /**
     * Updates an existing {@link Otp} record in persistence.
     *
     * <p>Called after {@link Otp#regenerate()} to persist the new code
     * and expiry while keeping the same identity (id, userId, type).
     *
     * @param otp the OTP to update; must not be {@code null}
     */
    void update(@NonNull Otp otp);

    /**
     * Deletes an {@link Otp} by its identifier.
     *
     * @param id the identifier of the OTP to delete; must not be {@code null}
     */
    void deleteById(@NonNull OtpId id);
}