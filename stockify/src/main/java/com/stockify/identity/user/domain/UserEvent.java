package com.stockify.identity.user.domain;

import com.stockify.shared.vo.UserId;
import org.jmolecules.event.types.DomainEvent;
import org.jspecify.annotations.NonNull;

import java.time.Instant;

/**
 * Sealed hierarchy of domain events published by the {@link User} aggregate.
 *
 * <p>Every event carries the affected {@link UserId} and the {@link Instant} at which
 * it occurred. Consumers can exhaustively pattern-match on the permitted subtypes
 * without needing an else branch.
 */
public sealed interface UserEvent extends DomainEvent permits
        UserEvent.Created,
        UserEvent.StatusChanged {

    /**
     * @return the id of the user this event relates to
     */
    @NonNull UserId id();

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
                @NonNull Email email,
                @NonNull UserStatus status
        ) {
            this(id, email, status, Instant.now());
        }
    }

    /**
     * Published when a {@link User} account transitions from one {@link UserStatus} to another.
     *
     * @param id         the id of the affected user
     * @param oldStatus  the status before the transition
     * @param newStatus  the status after the transition
     * @param occurredAt the instant the status changed
     */
    record StatusChanged(
            @NonNull UserId id,
            @NonNull UserStatus oldStatus,
            @NonNull UserStatus newStatus,
            @NonNull Instant occurredAt
    ) implements UserEvent {

        /**
         * Convenience constructor that defaults {@code occurredAt} to {@link Instant#now()}.
         *
         * @param id        the id of the affected user
         * @param oldStatus the status before the transition
         * @param newStatus the status after the transition
         */
        public StatusChanged(
                @NonNull UserId id,
                @NonNull UserStatus oldStatus,
                @NonNull UserStatus newStatus
        ) {
            this(id, oldStatus, newStatus, Instant.now());
        }
    }
}
