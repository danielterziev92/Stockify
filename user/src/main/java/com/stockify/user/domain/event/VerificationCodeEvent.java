package com.stockify.user.domain.event;

import com.stockify.user.domain.vo.Email;
import com.stockify.user.domain.vo.UserId;
import com.stockify.user.domain.vo.VerificationCodeId;
import org.jmolecules.event.types.DomainEvent;
import org.jspecify.annotations.NonNull;

import java.time.Instant;

/**
 * Domain events published by the {@link com.stockify.user.domain.VerificationCode} aggregate.
 *
 * <p>Each event is a sealed record — exhaustive pattern matching is enforced
 * by the compiler. Consumers must handle all three variants:
 * {@link Created}, {@link Verified}, and {@link Invalidated}.
 *
 * <p>Every event carries {@link #id()} and {@link #userId()} so consumers
 * can act without loading the aggregate, and {@link #occurredAt()} for
 * audit and ordering purposes.
 */
public sealed interface VerificationCodeEvent extends DomainEvent permits
        VerificationCodeEvent.Created,
        VerificationCodeEvent.Verified,
        VerificationCodeEvent.Invalidated {

    /**
     * The identifier of the verification code that produced this event.
     */
    @NonNull VerificationCodeId id();

    /**
     * The identifier of the user this verification code belongs to.
     */
    @NonNull UserId userId();

    /**
     * The wall-clock time at which this event occurred.
     */
    @NonNull Instant occurredAt();

    /**
     * Published when a new verification code is generated.
     *
     * <p>Intended consumers:
     * <ul>
     *   <li>Notification service — sends the verification email to {@link #email()}
     *       and constructs the verification link using {@link #code()},
     *       {@link #expireAt()}, and {@link #redirectUrl()}.</li>
     * </ul>
     *
     * @param id          the identifier of the generated code
     * @param userId      the identifier of the owning user
     * @param code        the numeric code to include in the verification link
     * @param expireAt    the point in time after which the code is no longer valid
     * @param redirectUrl the URL the client should be sent to after clicking the link
     * @param email       the address to which the verification email will be sent
     * @param occurredAt  the time at which the code was generated
     */
    record Created(
            @NonNull VerificationCodeId id,
            @NonNull UserId userId,
            @NonNull String code,
            @NonNull Instant expireAt,
            @NonNull String redirectUrl,
            @NonNull Email email,
            @NonNull Instant occurredAt
    ) implements VerificationCodeEvent {
        public Created(
                @NonNull VerificationCodeId id,
                @NonNull UserId userId,
                @NonNull String code,
                @NonNull Instant expireAt,
                @NonNull String redirectUrl,
                @NonNull Email email) {
            this(id, userId, code, expireAt, redirectUrl, email, Instant.now());
        }
    }

    /**
     * Published when a user successfully submits the correct verification code.
     *
     * <p>Intended consumers:
     * <ul>
     *   <li>Application service — activates the user account and deletes
     *       the code from the repository.</li>
     *   <li>Notification service — may send a welcome email.</li>
     * </ul>
     *
     * @param id          the identifier of the verified code
     * @param userId      the identifier of the user who verified the code
     * @param code        the numeric code that was submitted
     * @param redirectUrl the URL the client should be redirected to after verification
     * @param occurredAt  the time at which the verification occurred
     */
    record Verified(
            @NonNull VerificationCodeId id,
            @NonNull UserId userId,
            @NonNull String code,
            @NonNull String redirectUrl,
            @NonNull Instant occurredAt
    ) implements VerificationCodeEvent {
        public Verified(
                @NonNull VerificationCodeId id,
                @NonNull UserId userId,
                @NonNull String code,
                @NonNull String redirectUrl) {
            this(id, userId, code, redirectUrl, Instant.now());
        }
    }

    /**
     * Published when a verification code is explicitly invalidated before use.
     *
     * <p>Typical triggers: the user requests a new code (re-generation)
     * or an administrator revokes the code manually.
     *
     * <p>Intended consumers:
     * <ul>
     *   <li>Application service — deletes the code from the repository.</li>
     * </ul>
     *
     * @param id         the identifier of the invalidated code
     * @param userId     the identifier of the owning user
     * @param occurredAt the time at which the invalidation occurred
     */
    record Invalidated(
            @NonNull VerificationCodeId id,
            @NonNull UserId userId,
            @NonNull Instant occurredAt
    ) implements VerificationCodeEvent {
        public Invalidated(@NonNull VerificationCodeId id, @NonNull UserId userId) {
            this(id, userId, Instant.now());
        }
    }
}
