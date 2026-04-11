package com.stockify.identity.user;

import org.jmolecules.event.types.DomainEvent;
import org.jspecify.annotations.NonNull;

import java.time.Instant;

/**
 * Domain events published by the {@link Profile} aggregate.
 * <p>
 * Each permitted subtype represents a discrete state change that occurred to a profile.
 * All events carry the {@link UserId} of the affected user and the {@link Instant}
 * at which the change occurred.
 */
public sealed interface ProfileEvent extends DomainEvent permits
        ProfileEvent.Created,
        ProfileEvent.FirstNameChanged,
        ProfileEvent.LastNameChanged,
        ProfileEvent.PhoneNumberChanged {

    /**
     * Returns the identifier of the user this event relates to.
     *
     * @return the user ID, never {@code null}
     */
    @NonNull UserId id();

    /**
     * Returns the instant at which this event occurred.
     *
     * @return the occurrence timestamp, never {@code null}
     */
    @NonNull Instant occurredAt();

    /**
     * Published when a new {@link Profile} is created.
     *
     * @param id          the identifier of the user whose profile was created
     * @param firstName   the profile's initial first name
     * @param lastName    the profile's initial last name
     * @param phoneNumber the profile's initial phone number
     * @param occurredAt  the instant the profile was created
     */
    record Created(
            @NonNull UserId id,
            @NonNull String firstName,
            @NonNull String lastName,
            @NonNull PhoneNumber phoneNumber,
            @NonNull Instant occurredAt
    ) implements ProfileEvent {
        /**
         * Convenience constructor that sets {@code occurredAt} to {@link Instant#now()}.
         */
        public Created(
                @NonNull UserId id,
                @NonNull String firstName,
                @NonNull String lastName,
                @NonNull PhoneNumber phoneNumber) {
            this(id, firstName, lastName, phoneNumber, Instant.now());
        }
    }

    /**
     * Published when the first name of a {@link Profile} is changed.
     *
     * @param id           the identifier of the affected user
     * @param oldFirstName the first name before the change
     * @param newFirstName the first name after the change
     * @param occurredAt   the instant the change occurred
     */
    record FirstNameChanged(
            @NonNull UserId id,
            @NonNull String oldFirstName,
            @NonNull String newFirstName,
            @NonNull Instant occurredAt
    ) implements ProfileEvent {
        /**
         * Convenience constructor that sets {@code occurredAt} to {@link Instant#now()}.
         */
        public FirstNameChanged(
                @NonNull UserId id,
                @NonNull String oldFirstName,
                @NonNull String newFirstName) {
            this(id, oldFirstName, newFirstName, Instant.now());
        }
    }

    /**
     * Published when the last name of a {@link Profile} is changed.
     *
     * @param id          the identifier of the affected user
     * @param oldLastName the last name before the change
     * @param newLastName the last name after the change
     * @param occurredAt  the instant the change occurred
     */
    record LastNameChanged(
            @NonNull UserId id,
            @NonNull String oldLastName,
            @NonNull String newLastName,
            @NonNull Instant occurredAt
    ) implements ProfileEvent {
        /**
         * Convenience constructor that sets {@code occurredAt} to {@link Instant#now()}.
         */
        public LastNameChanged(
                @NonNull UserId id,
                @NonNull String oldLastName,
                @NonNull String newLastName) {
            this(id, oldLastName, newLastName, Instant.now());
        }
    }

    /**
     * Published when the phone number of a {@link Profile} is changed.
     *
     * @param id             the identifier of the affected profile
     * @param oldPhoneNumber the phone number before the change
     * @param newPhoneNumber the phone number after the change
     * @param occurredAt     the instant the change occurred
     */
    record PhoneNumberChanged(
            @NonNull UserId id,
            @NonNull PhoneNumber oldPhoneNumber,
            @NonNull PhoneNumber newPhoneNumber,
            @NonNull Instant occurredAt
    ) implements ProfileEvent {
        /**
         * Convenience constructor that sets {@code occurredAt} to {@link Instant#now()}.
         */
        public PhoneNumberChanged(
                @NonNull UserId id,
                @NonNull PhoneNumber oldPhoneNumber,
                @NonNull PhoneNumber newPhoneNumber) {
            this(id, oldPhoneNumber, newPhoneNumber, Instant.now());
        }
    }
}
