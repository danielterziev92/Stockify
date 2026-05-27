package com.stockify.identity.domain.user;

import com.stockify.shared.vo.UserId;
import lombok.Getter;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Aggregate root representing a user account in the identity domain.
 *
 * <p>All state mutations go through domain methods ({@link #activate()},
 * {@link #deactivate()}, {@link #validatePassword(String)})
 * which enforce invariants and record {@link UserEvent}s. Callers retrieve
 * those events via {@link #pullEvents()} and are responsible for publishing them.
 *
 * <p>Use {@link #create} for new accounts and {@link #reconstitute} to rebuild
 * the aggregate from persisted state without re-emitting creation events.
 *
 * <p><strong>Password ownership:</strong> Stockify validates password strength on
 * creation and change operations but never persists the password — Keycloak is the
 * authoritative store for credentials.
 */
@Getter
public class User implements AggregateRoot<User, UserId> {

    private final UserId id;
    private final Email email;
    private UserStatus status;
    private final Password password;

    private final List<UserEvent> events;

    private User(
            @NonNull UserId id,
            @NonNull Email email,
            @NonNull UserStatus status,
            @Nullable Password password
    ) {
        this.id = id;
        this.status = status;
        this.email = email;
        this.password = password;
        this.events = new ArrayList<>(2);
    }

    /**
     * Creates a new user account with {@link UserStatus#PENDING_VERIFICATION} status
     * and publishes a {@link UserEvent.Created} event.
     *
     * <p>The password is validated against all strength rules defined in
     * {@link UserRule.Password} but is never persisted — it is forwarded to
     * Keycloak during registration and then discarded.
     *
     * @param email    the raw email address string; normalized and validated by {@link Email}
     * @param password the plain-text password; validated by {@link Password} against
     *                 length and strength rules before being passed to Keycloak
     * @return a new {@code User} instance in {@link UserStatus#PENDING_VERIFICATION}
     * with one pending {@link UserEvent.Created} event
     */
    public static @NonNull User create(@NonNull String email, @NonNull String password) {
        User user = new User(
                UserId.generate(),
                new Email(email),
                UserStatus.PENDING_VERIFICATION,
                new Password(password)
        );

        user.events.add(new UserEvent.Created(user.id, user.email, user.status));
        return user;
    }

    /**
     * Rebuilds a {@code User} from persisted state without publishing any events.
     *
     * <p>Use this overload when rehydrating a user whose password is not needed
     * in the current operation (e.g., read-only queries, status transitions).
     *
     * @param id     the persisted user identifier
     * @param email  the persisted email address
     * @param status the persisted account status
     * @return a {@code User} reflecting the stored state, with {@code password = null}
     */
    public static @NonNull User reconstitute(
            @NonNull UserId id,
            @NonNull Email email,
            @NonNull UserStatus status
    ) {
        return new User(id, email, status, null);
    }

    /**
     * Rebuilds a {@code User} from persisted state without publishing any events.
     *
     * @param id       the persisted user identifier
     * @param email    the persisted email address
     * @param status   the persisted account status
     * @param password the previously validated password value object
     * @return a {@code User} reflecting the stored state
     */
    public static @NonNull User reconstitute(
            @NonNull UserId id,
            @NonNull Email email,
            @NonNull UserStatus status,
            @NonNull Password password
    ) {
        return new User(id, email, status, password);
    }

    /**
     * Transitions the account to {@link UserStatus#ACTIVE}.
     *
     * <p>No-op if the account is already active. Publishes a
     * {@link UserEvent.StatusChanged} otherwise.
     */
    public void activate() {
        if (this.status == UserStatus.ACTIVE) return;

        this.changeStatus(UserStatus.ACTIVE);
    }

    /**
     * Transitions the account to {@link UserStatus#INACTIVE} and publishes
     * a {@link UserEvent.StatusChanged}.
     *
     * @throws com.stockify.shared.exception.BusinessRuleException if the account
     *                                                             is not currently active
     */
    public void deactivate() {
        this.status.requiredActive();

        this.changeStatus(UserStatus.INACTIVE);
    }

    /**
     * Validates that {@code newPassword} satisfies all strength rules defined in
     * {@link UserRule.Password}.
     *
     * <p>Current password verification and the actual credential update are handled
     * outside the domain — by the application service via Keycloak.
     *
     * @param password the new plain-text password to validate
     * @throws com.stockify.shared.exception.BusinessRuleException if the account is not active
     * @throws com.stockify.shared.exception.InvalidValueException if {@code newPassword}
     *                                                             violates any strength rule
     */
    public void validatePassword(@NonNull String password) {
        this.status.requiredActive();

        new Password(password);
    }

    /**
     * Returns all pending domain events and clears the internal event list.
     *
     * <p>Callers are responsible for publishing the returned events before
     * the aggregate is persisted.
     *
     * @return an immutable snapshot of the pending events
     */
    public @NonNull List<UserEvent> pullEvents() {
        List<UserEvent> snapshot = List.copyOf(this.events);
        this.events.clear();
        return snapshot;
    }

    /**
     * Returns an immutable snapshot of the pending domain events without clearing them.
     *
     * <p>Use {@link #pullEvents()} instead when you want to consume and clear the events
     * in a single operation.
     *
     * @return an immutable view of the current pending events
     */
    public @NonNull List<UserEvent> getEvents() {
        return List.copyOf(this.events);
    }

    private void changeStatus(UserStatus newStatus) {
        UserStatus oldStatus = this.status;
        this.status = newStatus;
        this.events.add(new UserEvent.StatusChanged(this.id, oldStatus, newStatus));
    }
}
