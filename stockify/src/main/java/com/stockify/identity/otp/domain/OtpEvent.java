package com.stockify.identity.otp.domain;

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
 *
 * <p><strong>Security:</strong> {@link Created} and {@link Reissued} carry the
 * plaintext OTP {@code code} so an in-process listener can dispatch the
 * verification email. These events must never be externalized (e.g., routed to
 * Kafka via {@code spring-modulith-events-kafka}); doing so would write the code
 * to an external topic. They are intentionally <em>not</em> annotated
 * {@code @Externalized} and must stay that way.
 */
public sealed interface OtpEvent extends DomainEvent permits
        OtpEvent.Created,
        OtpEvent.Reissued,
        OtpEvent.Verified {

    /**
     * @return the identifier of the OTP this event relates to
     */
    @NonNull OtpId id();

    /**
     * @return the identifier of the user the OTP belongs to
     */
    @NonNull UserId userId();

    /**
     * @return the purpose for which the OTP was generated
     */
    @NonNull OtpType type();

    /**
     * @return the instant at which the event occurred
     */
    @NonNull Instant occurredAt();

    /**
     * Published when a brand-new {@link Otp} is generated (e.g., at registration).
     *
     * @param id         the identifier of the newly created OTP
     * @param userId     the identifier of the user the OTP belongs to
     * @param type       the purpose for which the OTP was generated
     * @param code       the plaintext code to be emailed to the user
     * @param occurredAt the instant the OTP was created
     */
    record Created(
            @NonNull OtpId id,
            @NonNull UserId userId,
            @NonNull OtpType type,
            @NonNull String code,
            @NonNull Instant occurredAt
    ) implements OtpEvent {

        /**
         * Convenience constructor that defaults {@code occurredAt} to {@link Instant#now()}.
         */
        public Created(@NonNull OtpId id, @NonNull UserId userId, @NonNull OtpType type, @NonNull String code) {
            this(id, userId, type, code, Instant.now());
        }
    }


    /**
     * Published when an existing {@link Otp} is regenerated with a fresh code and
     * expiry (e.g., on an explicit resend request).
     *
     * @param id         the identifier of the OTP that was reissued
     * @param userId     the identifier of the user the OTP belongs to
     * @param type       the purpose for which the OTP was originally generated
     * @param code       the new plaintext code to be emailed to the user
     * @param occurredAt the instant the OTP was reissued
     */
    record Reissued(
            @NonNull OtpId id,
            @NonNull UserId userId,
            @NonNull OtpType type,
            @NonNull String code,
            @NonNull Instant occurredAt
    ) implements OtpEvent {

        /**
         * Convenience constructor that defaults {@code occurredAt} to {@link Instant#now()}.
         */
        public Reissued(@NonNull OtpId id, @NonNull UserId userId, @NonNull OtpType type, @NonNull String code) {
            this(id, userId, type, code, Instant.now());
        }
    }

    /**
     * Published when an {@link Otp} is successfully verified.
     *
     * <p>Carries no code — verification is complete, and the OTP is about to be deleted.
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
         */
        public Verified(@NonNull OtpId id, @NonNull UserId userId, @NonNull OtpType type) {
            this(id, userId, type, Instant.now());
        }
    }
}
