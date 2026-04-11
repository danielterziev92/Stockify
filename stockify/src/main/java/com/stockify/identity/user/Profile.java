package com.stockify.identity.user;

import lombok.Getter;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Aggregate root representing the personal profile of a user.
 * <p>
 * Identified by a {@link UserId} and holds mutable personal information:
 * first name, last name, and phone number. Every state change produces a
 * corresponding {@link ProfileEvent} that is accumulated in-memory and can
 * be drained via {@link #pullEvents()} for publication.
 */
@Getter
public class Profile implements AggregateRoot<Profile, UserId> {

    private final UserId id;
    private String firstName;
    private String lastName;
    private PhoneNumber phoneNumber;

    private final List<ProfileEvent> events;

    private Profile(
            @NonNull UserId id,
            @NonNull String firstName,
            @NonNull String lastName,
            @NonNull PhoneNumber phoneNumber
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.events = new ArrayList<>(3);
    }

    /**
     * Creates a new {@link Profile} and records a {@link ProfileEvent.Created} event.
     *
     * @param id          the {@link UserId} for the new profile
     * @param firstName   the initial first name
     * @param lastName    the initial last name
     * @param phoneNumber the initial phone number
     * @return the newly created profile, never {@code null}
     */
    public static @NonNull Profile create(
            @NonNull UserId id,
            @NonNull String firstName,
            @NonNull String lastName,
            @NonNull PhoneNumber phoneNumber
    ) {
        Profile profile = new Profile(id, firstName, lastName, phoneNumber);
        profile.events.add(new ProfileEvent.Created(id, firstName, lastName, phoneNumber));

        return profile;
    }

    /**
     * Reconstitutes an existing {@link Profile} from persistent state without raising
     * any domain events.
     * <p>
     * Use this factory when rehydrating a profile from storage rather than when
     * creating a brand-new one.
     *
     * @param id          the persisted {@link UserId}
     * @param firstName   the persisted first name
     * @param lastName    the persisted last name
     * @param phoneNumber the persisted phone number
     * @return the reconstituted profile, never {@code null}
     */
    public static @NonNull Profile reconstitute(
            @NonNull UserId id,
            @NonNull String firstName,
            @NonNull String lastName,
            @NonNull PhoneNumber phoneNumber
    ) {
        return new Profile(id, firstName, lastName, phoneNumber);
    }

    /**
     * Changes the first name of this profile.
     * <p>
     * No-ops if the new value is identical to the current one. Otherwise, updates
     * the field and records a {@link ProfileEvent.FirstNameChanged} event.
     *
     * @param newFirstName the new first name, must not be {@code null}
     */
    public void changeFirstName(@NonNull String newFirstName) {
        if (this.firstName.equals(newFirstName)) return;

        String oldFirstName = this.firstName;
        this.firstName = newFirstName;

        this.events.add(new ProfileEvent.FirstNameChanged(this.id, oldFirstName, newFirstName));
    }

    /**
     * Changes the last name of this profile.
     * <p>
     * No-ops if the new value is identical to the current one. Otherwise updates
     * the field and records a {@link ProfileEvent.LastNameChanged} event.
     *
     * @param newLastName the new last name, must not be {@code null}
     */
    public void changeLastName(@NonNull String newLastName) {
        if (this.lastName.equals(newLastName)) return;

        String oldLastName = this.lastName;
        this.lastName = newLastName;

        this.events.add(new ProfileEvent.LastNameChanged(this.id, oldLastName, newLastName));
    }

    /**
     * Changes the phone number of this profile.
     * <p>
     * No-ops if the new value is equal to the current one. Otherwise, updates
     * the field and records a {@link ProfileEvent.PhoneNumberChanged} event.
     *
     * @param newPhoneNumber the new phone number, must not be {@code null}
     */
    public void changePhoneNumber(@NonNull PhoneNumber newPhoneNumber) {
        if (this.phoneNumber.equals(newPhoneNumber)) return;

        PhoneNumber oldPhoneNumber = this.phoneNumber;
        this.phoneNumber = newPhoneNumber;

        this.events.add(new ProfileEvent.PhoneNumberChanged(this.id, oldPhoneNumber, newPhoneNumber));
    }

    /**
     * Returns a snapshot of all accumulated events and clears the internal event list.
     * <p>
     * Use this method to collect events for publication after a command has been
     * processed, ensuring each event is dispatched exactly once.
     *
     * @return an immutable copy of the pending events, never {@code null}
     */
    public @NonNull List<ProfileEvent> pullEvents() {
        List<ProfileEvent> snapshot = List.copyOf(this.events);
        this.events.clear();
        return snapshot;
    }

    /**
     * Returns a read-only view of the currently accumulated events without clearing them.
     *
     * @return an immutable copy of the pending events, never {@code null}
     */
    public @NonNull List<ProfileEvent> getEvents() {
        return List.copyOf(this.events);
    }
}
