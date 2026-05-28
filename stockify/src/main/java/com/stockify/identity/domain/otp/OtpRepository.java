package com.stockify.identity.domain.otp;

import com.stockify.shared.vo.UserId;
import org.jmolecules.ddd.types.Repository;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

/**
 * Repository port for the {@link Otp} aggregate root.
 *
 * <p>Defines the persistence contract for {@code Otp} aggregates.
 * Implementations are provided by the infrastructure layer. The domain depends
 * only on this interface, keeping it free of persistence concerns.
 */
public interface OtpRepository extends Repository<Otp, OtpId> {

    /**
     * Looks up an {@link Otp} by its numeric code.
     *
     * @param code the 8-digit numeric code to search for; must not be {@code null}
     * @return an {@link Optional} containing the matching OTP, or empty if not found
     */
    Optional<Otp> findByCode(@NonNull String code);

    /**
     * Looks up an {@link Otp} by the owning user and OTP type.
     *
     * @param userId the identifier of the owning user; must not be {@code null}
     * @param type   the OTP type to search for; must not be {@code null}
     * @return an {@link Optional} containing the matching OTP, or empty if not found
     */
    Optional<Otp> findByUserIdAndType(@NonNull UserId userId, @NonNull OtpType type);

    /**
     * Persists a new {@link Otp}.
     *
     * @param otp the OTP to persist; must not be {@code null}
     */
    void save(@NonNull Otp otp);

    /**
     * Updates an existing {@link Otp}.
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