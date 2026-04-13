package com.stockify.identity.domain.verificationcode;

import com.stockify.shared.vo.UserId;
import org.jmolecules.event.types.DomainEvent;
import org.jspecify.annotations.NonNull;

import java.time.Instant;

/**
 * Domain events emitted by the {@link VerificationCode} aggregate.
 */
public sealed interface VerificationCodeEvent extends DomainEvent permits
        VerificationCodeEvent.Created,
        VerificationCodeEvent.Verified {

    /**
     * Returns the ID of the verification code that produced this event.
     */
    @NonNull VerificationCodeId id();

    /**
     * Returns the instant at which this event occurred.
     */
    @NonNull Instant occurredAt();

    /**
     * Raised when a new verification code is issued to a user.
     *
     * @param id         the ID of the new verification code
     * @param userId     the user the code was issued for
     * @param type       the purpose of the code
     * @param occurredAt when the code was created
     */
    record Created(
            @NonNull VerificationCodeId id,
            @NonNull UserId userId,
            @NonNull VerificationCodeType type,
            @NonNull String code,
            @NonNull Instant expiresAt,
            @NonNull Instant occurredAt
    ) implements VerificationCodeEvent {

        /**
         * Convenience constructor that sets {@code occurredAt} to {@link Instant#now()}.
         */
        public Created(
                @NonNull VerificationCodeId id,
                @NonNull UserId userId,
                @NonNull VerificationCodeType type,
                @NonNull String code,
                @NonNull Instant expiresAt) {
            this(id, userId, type, code, expiresAt, Instant.now());
        }
    }

    /**
     * Raised when a user successfully redeems a verification code.
     *
     * @param id         the ID of the redeemed verification code
     * @param userId     the user who redeemed the code
     * @param type       the purpose of the code
     * @param occurredAt when the code was verified
     */
    record Verified(
            @NonNull VerificationCodeId id,
            @NonNull UserId userId,
            @NonNull VerificationCodeType type,
            @NonNull Instant occurredAt
    ) implements VerificationCodeEvent {

        /**
         * Convenience constructor that sets {@code occurredAt} to {@link Instant#now()}.
         */
        public Verified(
                @NonNull VerificationCodeId id,
                @NonNull UserId userId,
                @NonNull VerificationCodeType type) {
            this(id, userId, type, Instant.now());
        }
    }
}
