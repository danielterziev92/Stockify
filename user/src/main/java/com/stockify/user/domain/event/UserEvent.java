package com.stockify.user.domain.event;

import com.stockify.user.domain.UserStatus;
import com.stockify.user.domain.vo.Email;
import com.stockify.user.domain.vo.UserId;
import org.jmolecules.event.types.DomainEvent;
import org.jspecify.annotations.NonNull;

import java.time.Instant;


/**
 * Domain events published by the {@link com.stockify.user.domain.User} aggregate.
 *
 * <p>Each event is a sealed record — exhaustive pattern matching is enforced
 * by the compiler. Consumers must handle all four variants:
 * {@link Created}, {@link StatusChanged}, {@link PasswordChanged},
 * and {@link PasswordResetRequested}.
 *
 * <p>Every event carries {@link #id()} so consumers can act without loading
 * the aggregate, and {@link #occurredAt()} for audit and ordering purposes.
 */
public sealed interface UserEvent extends DomainEvent permits
        UserEvent.Created,
        UserEvent.StatusChanged,
        UserEvent.PasswordChanged,
        UserEvent.PasswordResetRequested {

    /**
     * The identifier of the user that produced this event.
     */
    @NonNull UserId id();

    /**
     * The wall-clock time at which this event occurred.
     */
    @NonNull Instant occurredAt();

    /**
     * Published when a new user account is created.
     *
     * <p>Intended consumers:
     * <ul>
     *   <li>Application layer — creates a {@code VerificationCode} and triggers
     *       the verification email.</li>
     * </ul>
     *
     * @param id           the identifier of the created user
     * @param email        the email address of the new user
     * @param passwordHash the BCrypt hash of the user's chosen password,
     *                     persisted by the infrastructure layer via {@code INSERT}
     * @param status       the initial account status as a string
     * @param occurredAt   the time at which the account was created
     */
    record Created(
            @NonNull UserId id,
            @NonNull Email email,
            @NonNull String passwordHash,
            @NonNull String status,
            @NonNull Instant occurredAt
    ) implements UserEvent {
        public Created(
                @NonNull UserId id,
                @NonNull Email email,
                @NonNull String passwordHash,
                @NonNull UserStatus status) {
            this(id, email, status.toString(), passwordHash, Instant.now());
        }
    }

    /**
     * Published when the account transitions from one status to another.
     *
     * <p>Intended consumers:
     * <ul>
     *   <li>Infrastructure layer — persists the new status via {@code UPDATE}.</li>
     * </ul>
     *
     * @param id         the identifier of the affected user
     * @param oldStatus  the previous status as a string
     * @param newStatus  the new status as a string
     * @param occurredAt the time at which the transition occurred
     */
    record StatusChanged(
            @NonNull UserId id,
            @NonNull String oldStatus,
            @NonNull String newStatus,
            @NonNull Instant occurredAt
    ) implements UserEvent {
        public StatusChanged(
                @NonNull UserId id,
                @NonNull UserStatus oldStatus,
                @NonNull UserStatus newStatus) {
            this(id, oldStatus.toString(), newStatus.toString(), Instant.now());
        }
    }

    /**
     * Published when the user successfully changes their password.
     *
     * <p>The new hash is carried in the event so the infrastructure layer can
     * issue a targeted {@code UPDATE} without loading the current hash from
     * the database.
     *
     * <p>Intended consumers:
     * <ul>
     *   <li>Infrastructure layer — persists {@link #passwordHash()} via {@code UPDATE}.</li>
     * </ul>
     *
     * @param id           the identifier of the affected user
     * @param passwordHash the BCrypt hash of the new password
     * @param occurredAt   the time at which the password was changed
     */
    record PasswordChanged(
            @NonNull UserId id,
            @NonNull String passwordHash,
            @NonNull Instant occurredAt
    ) implements UserEvent {
        public PasswordChanged(@NonNull UserId id, @NonNull String passwordHash) {
            this(id, passwordHash, Instant.now());
        }
    }

    /**
     * Published when the user requests a password reset link.
     *
     * <p>The application layer embeds {@link #version()} in a signed HMAC token
     * sent to {@link #email()}. Because Spring Data JDBC increments the aggregate
     * version on every {@code UPDATE}, any subsequent writing invalidates all
     * previously issued reset links.
     *
     * <p>Intended consumers:
     * <ul>
     *   <li>Application layer — generates the HMAC token and dispatches
     *       the reset email to {@link #email()}.</li>
     * </ul>
     *
     * @param id         the identifier of the affected user
     * @param email      the address to which the reset link will be sent
     * @param version    the current aggregate version embedded in the reset token
     * @param occurredAt the time at which the reset was requested
     */
    record PasswordResetRequested(
            @NonNull UserId id,
            @NonNull Email email,
            int version,
            @NonNull Instant occurredAt
    ) implements UserEvent {
        public PasswordResetRequested(
                @NonNull UserId id,
                @NonNull Email email,
                int version) {
            this(id, email, version, Instant.now());
        }
    }
}
