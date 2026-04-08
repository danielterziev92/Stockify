package com.stockify.user.domain;

import com.stockify.shared.exception.InvalidValueException;
import com.stockify.user.domain.event.UserEvent;
import com.stockify.user.domain.rule.UserRule;
import com.stockify.user.domain.vo.Email;
import com.stockify.user.domain.vo.UserId;
import lombok.Getter;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jspecify.annotations.NonNull;
import org.springframework.data.annotation.Version;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Aggregate root representing a user account.
 *
 * <p>Responsibilities:
 * <ul>
 *   <li>Enforcing BCrypt password hash validity on creation and change.</li>
 *   <li>Guarding status-sensitive operations via {@link UserStatus#requiredActive()}.</li>
 *   <li>Tracking the {@link #version} used both for optimistic locking and
 *       invalidation of outstanding password-reset links.</li>
 * </ul>
 */
@Getter
public class User implements AggregateRoot<User, UserId> {

    private final UserId id;
    private final Email email;
    private String passwordHash;
    private UserStatus status;

    /**
     * Optimistic locking version, managed exclusively by Spring Data JDBC.
     */
    @Version
    private int version;

    private final List<UserEvent> events;

    private User(
            @NonNull UserId id,
            @NonNull Email email,
            @NonNull String passwordHash,
            @NonNull UserStatus status,
            int version
    ) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.status = status;
        this.version = version;
        this.events = new ArrayList<>();
    }

    /**
     * Creates a new user account in {@link UserStatus#PENDING_ACTIVATION} and emits
     * {@link UserEvent.Created}.
     *
     * @param email        the email address of the new user
     * @param passwordHash a BCrypt hash of the user's chosen password
     * @return a new {@code User} ready to be persisted
     * @throws InvalidValueException if {@code passwordHash} is blank,
     *                               has an incorrect length,
     *                               or does not match the BCrypt format
     */
    public static @NonNull User create(
            @NonNull Email email,
            @NonNull String passwordHash
    ) {
        validatePasswordHash(passwordHash);

        User user = new User(UserId.generate(), email, passwordHash, UserStatus.PENDING_ACTIVATION, 0);
        user.events.add(new UserEvent.Created(user.id, user.email, user.passwordHash, user.status));

        return user;
    }

    /**
     * Reconstitutes a {@code User} from its persisted state.
     * No domain events are emitted.
     *
     * @param id           the persisted aggregate identifier
     * @param email        the user's email address
     * @param passwordHash the BCrypt hash loaded from the database,
     *                     used for password comparison in {@link #changePassword(String)}
     * @param status       the current account status
     * @param version      the current optimistic locking version
     * @return the reconstituted aggregate
     */
    public static @NonNull User reconstitute(
            @NonNull UserId id,
            @NonNull Email email,
            @NonNull String passwordHash,
            @NonNull UserStatus status,
            int version
    ) {
        return new User(id, email, passwordHash, status, version);
    }

    /**
     * Transitions the account to a new status and emits {@link UserEvent.StatusChanged}.
     * If the given status equals the current one, this method is a no-op.
     *
     * @param status the new status to transition to
     */
    public void changeStatus(@NonNull UserStatus status) {
        if (this.status.equals(status)) return;

        UserStatus oldStatus = this.status;
        this.status = status;
        this.events.add(new UserEvent.StatusChanged(this.id, oldStatus, this.status));
    }

    /**
     * Replaces the password hash if it differs from the current one and emits
     * {@link UserEvent.PasswordChanged}.
     *
     * <p>Both the incoming and the stored value are BCrypt hashes — no plain-text
     * password is ever handled here. If the new hash equals the current one,
     * this method is a no-op.
     * Requires the account to be {@link UserStatus#ACTIVE}.
     *
     * @param passwordHash a BCrypt hash of the user's new password
     * @throws com.stockify.shared.exception.BusinessRuleException if the account is not active
     * @throws InvalidValueException                               if {@code passwordHash} is blank,
     *                                                             has an incorrect length,
     *                                                             or does not match the BCrypt format
     */
    public void changePassword(@NonNull String passwordHash) {
        this.status.requiredActive();
        if (this.passwordHash.equals(passwordHash)) return;

        validatePasswordHash(passwordHash);

        this.passwordHash = passwordHash;
        this.events.add(new UserEvent.PasswordChanged(this.id, this.passwordHash));
    }

    /**
     * Initiates a password reset flow by emitting {@link UserEvent.PasswordResetRequested}.
     *
     * <p>The event carries the current {@link #version}, which the application layer
     * embeds in a signed HMAC token sent to the user's email address. Because
     * Spring Data JDBC increments {@link #version} on every {@code UPDATE}, any
     * subsequent writing to this aggregate invalidates all previously issued reset links.
     * Requires the account to be {@link UserStatus#ACTIVE}.
     *
     * @throws com.stockify.shared.exception.BusinessRuleException if the account is not active
     */
    public void requestPasswordReset() {
        this.status.requiredActive();
        this.events.add(new UserEvent.PasswordResetRequested(this.id, this.email, this.version));
    }

    /**
     * Drains and returns all pending domain events.
     * Should be called by the repository after persisting the aggregate.
     */
    public @NonNull List<UserEvent> pullEvents() {
        List<UserEvent> snapshot = List.copyOf(this.events);
        this.events.clear();
        return snapshot;
    }

    /**
     * Returns a read-only view of pending events without clearing them.
     * Intended for testing and diagnostics.
     */
    public @NonNull List<UserEvent> getEvents() {
        return Collections.unmodifiableList(this.events);
    }

    private static void validatePasswordHash(@NonNull String passwordHash) {
        if (passwordHash.isBlank()) throw new InvalidValueException(UserRule.PasswordHash.BLANK_MSG);
        if (passwordHash.length() != UserRule.PasswordHash.BCRYPT_LENGTH)
            throw new InvalidValueException(UserRule.PasswordHash.BCRYPT_LENGTH_MSG);
        if (!UserRule.PasswordHash.BCRYPT_PATTERN.matcher(passwordHash).matches())
            throw new InvalidValueException(UserRule.PasswordHash.BCRYPT_PATTERN_MSG);
    }
}
