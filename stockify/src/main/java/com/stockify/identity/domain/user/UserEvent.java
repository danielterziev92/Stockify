package com.stockify.identity.domain.user;

import com.stockify.shared.vo.UserId;
import jakarta.annotation.Nonnull;
import org.jmolecules.event.types.DomainEvent;
import org.jspecify.annotations.NonNull;

import java.time.Instant;

/**
 * Sealed hierarchy of domain events published by the {@link User} aggregate.
 *
 * <p>Every event carries the affected {@link Email} and the {@link Instant} at which
 * it occurred. Consumers can exhaustively pattern-match on the permitted subtypes
 * without needing an else branch.
 */
public sealed interface UserEvent extends DomainEvent permits
        UserEvent.Created,
        UserEvent.ChangeStatus,
        UserEvent.PasswordChanged,
        UserEvent.ResetPassword {

    /**
     * @return the email address of the user this event relates to
     */
    @NonNull Email email();

    /**
     * @return the instant at which the event occurred
     */
    @NonNull Instant occurredAt();

    /**
     * Published when a new {@link User} account is created.
     *
     * @param id         the identifier of the newly created user
     * @param email      the email address of the new user
     * @param status     the initial account status
     * @param occurredAt the instant the user was created
     */
    record Created(
            @NonNull UserId id,
            @NonNull Email email,
            @NonNull UserStatus status,
            @NonNull Instant occurredAt
    ) implements UserEvent {

        /**
         * Convenience constructor that defaults {@code occurredAt} to {@link Instant#now()}.
         *
         * @param id     the identifier of the newly created user
         * @param email  the email address of the new user
         * @param status the initial account status
         */
        public Created(
                @NonNull UserId id,
                @Nonnull Email email,
                @NonNull UserStatus status) {
            this(id, email, status, Instant.now());
        }
    }

    /**
     * Published when a {@link User} account transitions from one {@link UserStatus} to another.
     *
     * @param email      the email address of the affected user
     * @param oldStatus  the status before the transition
     * @param newStatus  the status after the transition
     * @param occurredAt the instant the status changed
     */
    record ChangeStatus(
            @NonNull Email email,
            @NonNull UserStatus oldStatus,
            @NonNull UserStatus newStatus,
            @NonNull Instant occurredAt
    ) implements UserEvent {

        /**
         * Convenience constructor that defaults {@code occurredAt} to {@link Instant#now()}.
         *
         * @param email     the email address of the affected user
         * @param oldStatus the status before the transition
         * @param newStatus the status after the transition
         */
        public ChangeStatus(@NonNull Email email, @NonNull UserStatus oldStatus, @NonNull UserStatus newStatus) {
            this(email, oldStatus, newStatus, Instant.now());
        }
    }

    /**
     * Published when a {@link User} successfully changes their password.
     *
     * @param email      the email address of the affected user
     * @param occurredAt the instant the password was changed
     */
    record PasswordChanged(
            @NonNull Email email,
            @NonNull Instant occurredAt
    ) implements UserEvent {

        /**
         * Convenience constructor that defaults {@code occurredAt} to {@link Instant#now()}.
         *
         * @param email the email address of the affected user
         */
        public PasswordChanged(@NonNull Email email) {
            this(email, Instant.now());
        }
    }

    /**
     * Published when a {@link User} password is reset, optionally accompanied by a status change.
     *
     * @param email      the email address of the affected user
     * @param status     the account status at the time of the reset
     * @param occurredAt the instant the password was reset
     */
    record ResetPassword(
            @NonNull Email email,
            @NonNull UserStatus status,
            @NonNull Instant occurredAt
    ) implements UserEvent {

        /**
         * Convenience constructor that defaults {@code occurredAt} to {@link Instant#now()}.
         *
         * @param email  the email address of the affected user
         * @param status the account status at the time of the reset
         */
        public ResetPassword(@NonNull Email email, @NonNull UserStatus status) {
            this(email, status, Instant.now());
        }
    }
}
