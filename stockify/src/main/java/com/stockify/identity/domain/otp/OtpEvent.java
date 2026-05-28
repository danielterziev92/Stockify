package com.stockify.identity.domain.otp;

import com.stockify.shared.vo.UserId;
import org.jmolecules.event.types.DomainEvent;
import org.jspecify.annotations.NonNull;

import java.time.Instant;

/**
 * Sealed hierarchy of domain events published by the {@link Otp} aggregate.
 *
 * <p>Every event carries the {@link OtpId}, the {@link UserId} of the owner,
 * and the {@link Instant} at which it occurred. Consumers can exhaustively
 * pattern-match on the permitted subtypes without needing an else branch.
 */
public sealed interface OtpEvent extends DomainEvent permits
        OtpEvent.Created,
        OtpEvent.Verified {

    /**
     * @return the identifier of the OTP this event relates to
     */
    @NonNull OtpId id();

    /**
     * @return the instant at which the event occurred
     */
    @NonNull Instant occurredAt();

    /**
     * Published when a new {@link Otp} is generated.
     *
     * @param id         the identifier of the newly created OTP
     * @param userId     the identifier of the user the OTP belongs to
     * @param type       the purpose for which the OTP was generated
     * @param occurredAt the instant the OTP was created
     */
    record Created(
            @NonNull OtpId id,
            @NonNull UserId userId,
            @NonNull OtpType type,
            @NonNull Instant occurredAt
    ) implements OtpEvent {

        /**
         * Convenience constructor that defaults {@code occurredAt} to {@link Instant#now()}.
         *
         * @param id     the identifier of the newly created OTP
         * @param userId the identifier of the user the OTP belongs to
         * @param type   the purpose for which the OTP was generated
         */
        public Created(@NonNull OtpId id, @NonNull UserId userId, @NonNull OtpType type) {
            this(id, userId, type, Instant.now());
        }
    }

    /**
     * Published when an {@link Otp} is successfully verified.
     *
     * @param id         the identifier of the verified OTP
     * @param userId     the identifier of the user who submitted the OTP
     * @param type       the purpose for which the OTP was originally generated
     * @param occurredAt the instant the OTP was verified
     */
    record Verified(
            @NonNull OtpId id,
            @NonNull UserId userId,
            @NonNull OtpType type,
            @NonNull Instant occurredAt
    ) implements OtpEvent {

        /**
         * Convenience constructor that defaults {@code occurredAt} to {@link Instant#now()}.
         *
         * @param id     the identifier of the verified OTP
         * @param userId the identifier of the user who submitted the OTP
         * @param type   the purpose for which the OTP was originally generated
         */
        public Verified(@NonNull OtpId id, @NonNull UserId userId, @NonNull OtpType type) {
            this(id, userId, type, Instant.now());
        }
    }
}
