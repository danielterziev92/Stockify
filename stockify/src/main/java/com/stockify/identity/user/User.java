package com.stockify.identity.user;

import lombok.Getter;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Aggregate root representing a user account in the tenancy domain.
 *
 * <p>All state mutations go through domain methods ({@link #activate()},
 * {@link #deactivate()}, {@link #changePassword}, {@link #resetPassword()})
 * which enforce invariants and record {@link UserEvent}s. Callers retrieve
 * those events via {@link #pullEvents()} and are responsible for publishing them.
 *
 * <p>Use {@link #create} for new accounts and {@link #reconstitute} to rebuild
 * the aggregate from persisted state without re-emitting creation events.
 */
@Getter
public class User implements AggregateRoot<User, UserId> {

    private final UserId id;
    private final Email email;
    private UserStatus status;
    private PasswordHash passwordHash;

    private final List<UserEvent> events;

    private User(
            @NonNull UserId id,
            @NonNull Email email,
            @NonNull UserStatus status,
            @Nullable PasswordHash passwordHash
    ) {
        this.id = id;
        this.status = status;
        this.email = email;
        this.passwordHash = passwordHash;
        this.events = new ArrayList<>(5);
    }

    /**
     * Creates a new user account with {@link UserStatus#PENDING_VERIFICATION} status
     * and publishes a {@link UserEvent.Created} event.
     *
     * @param email        the raw email address string; normalized and validated by {@link Email}
     * @param passwordHash the raw BCrypt hash string; validated by {@link PasswordHash}
     * @return a new {@code User} instance with one pending event
     */
    public static @NonNull User create(
            @NonNull String email,
            @NonNull String passwordHash
    ) {
        User user = new User(
                UserId.generate(),
                new Email(email),
                UserStatus.PENDING_VERIFICATION,
                new PasswordHash(passwordHash)
        );

        user.events.add(new UserEvent.Created(user.id, user.email, user.status));

        return user;
    }

    /**
     * Rebuilds a {@code User} from persisted state without publishing any events.
     *
     * @param id           the persisted user identifier
     * @param email        the persisted email address
     * @param status       the persisted account status
     * @param passwordHash the persisted password hash, or {@code null} if not set
     * @return a {@code User} reflecting the stored state
     */
    public static @NonNull User reconstitute(
            @NonNull UserId id,
            @NonNull Email email,
            @NonNull UserStatus status,
            @Nullable PasswordHash passwordHash
    ) {
        return new User(id, email, status, passwordHash);
    }

    /**
     * Transitions the account to {@link UserStatus#ACTIVE}.
     *
     * <p>No-op if the account is already active. Publishes a {@link UserEvent.ChangeStatus} otherwise.
     */
    public void activate() {
        if (this.status == UserStatus.ACTIVE) return;

        UserStatus oldStatus = this.status;
        this.status = UserStatus.ACTIVE;
        this.events.add(new UserEvent.ChangeStatus(this.email, oldStatus, this.status));
    }

    /**
     * Transitions the account to {@link UserStatus#INACTIVE} and publishes a {@link UserEvent.ChangeStatus}.
     *
     * @throws com.stockify.shared.exception.BusinessRuleException if the account is not currently active
     */
    public void deactivate() {
        this.status.requiredActive();

        UserStatus oldStatus = this.status;
        this.status = UserStatus.INACTIVE;
        this.events.add(new UserEvent.ChangeStatus(this.email, oldStatus, this.status));
    }

    /**
     * Replaces the current password hash and publishes a {@link UserEvent.PasswordChanged}.
     *
     * <p>No-op if the new hash is identical to the current one.
     *
     * @param newPasswordHash the raw BCrypt hash string for the new password
     * @throws com.stockify.shared.exception.BusinessRuleException if the account is not currently active
     */
    public void changePassword(@NonNull String newPasswordHash) {
        this.status.requiredActive();

        Objects.requireNonNull(this.passwordHash);

        PasswordHash newPasswordHashVO = new PasswordHash(newPasswordHash);
        if (this.passwordHash.equals(newPasswordHashVO)) return;

        this.passwordHash = newPasswordHashVO;

        this.events.add(new UserEvent.PasswordChanged(this.email));
    }

    /**
     * Resets the password by setting the account status to {@link UserStatus#INACTIVE}
     * and publishes a {@link UserEvent.ResetPassword}.
     *
     * @throws com.stockify.shared.exception.BusinessRuleException if the account is not currently active
     */
    public void resetPassword() {
        this.status.requiredActive();

        this.status = UserStatus.INACTIVE;
        this.events.add(new UserEvent.ResetPassword(this.email, this.status));
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
}
