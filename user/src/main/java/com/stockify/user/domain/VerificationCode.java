package com.stockify.user.domain;

import com.stockify.shared.exception.BusinessRuleException;
import com.stockify.user.domain.event.VerificationCodeEvent;
import com.stockify.user.domain.rule.VerificationCodeRule;
import com.stockify.user.domain.vo.Email;
import com.stockify.user.domain.vo.UserId;
import com.stockify.user.domain.vo.VerificationCodeId;
import lombok.Getter;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jspecify.annotations.NonNull;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Aggregate root representing a one-time email verification code.
 *
 * <p>Lifecycle is intentionally simple — the code exists in one state only:
 * <strong>PENDING</strong>. Existence in the repository implies it is pending.
 * Once consumed (verified or invalidated), the application layer deletes the
 * record in response to the emitted domain event. There is therefore no need
 * to persist a status flag.
 */
@Getter
public class VerificationCode implements AggregateRoot<VerificationCode, VerificationCodeId> {

    private final VerificationCodeId id;
    private final UserId userId;

    /**
     * The generated numeric code sent to the user.
     */
    private final String code;

    /**
     * The point in time after which this code is no longer valid.
     */
    private final Instant expireAt;


    private final List<VerificationCodeEvent> events;

    private VerificationCode(
            VerificationCodeId id,
            UserId userId,
            String code,
            Instant expireAt
    ) {
        this.id = id;
        this.userId = userId;
        this.code = code;
        this.expireAt = expireAt;
        this.events = new ArrayList<>();
    }

    /**
     * Creates a new pending verification code and emits {@link VerificationCodeEvent.Created}.
     *
     * <p>The email address is included in the event, so the notification service
     * can send the verification email without an additional query.
     * The redirect URL is a presentation concern and is resolved by the
     * notification service from {@link VerificationCodeRule.RedirectUrl}.
     *
     * @param userId the identifier of the user this code belongs to
     * @param email  the address to which the verification link will be sent
     * @return a new {@code VerificationCode} ready to be persisted
     */
    public static @NonNull VerificationCode generate(@NonNull UserId userId, @NonNull Email email) {
        VerificationCode verificationCode = new VerificationCode(
                VerificationCodeId.generate(),
                userId,
                generateCode(),
                Instant.now().plusSeconds(VerificationCodeRule.ExpireAt.DURATION_IN_SECONDS)
        );

        verificationCode.events.add(new VerificationCodeEvent.Created(
                verificationCode.getId(),
                verificationCode.getUserId(),
                verificationCode.getCode(),
                verificationCode.getExpireAt(),
                VerificationCodeRule.RedirectUrl.AFTER_CREATION,
                email
        ));

        return verificationCode;
    }

    /**
     * Reconstitutes a {@code VerificationCode} from its persisted state.
     * No domain events are emitted.
     */
    public static @NonNull VerificationCode reconstitute(
            @NonNull VerificationCodeId id,
            @NonNull UserId userId,
            @NonNull String code,
            @NonNull Instant expireAt
    ) {
        return new VerificationCode(id, userId, code, expireAt);
    }

    /**
     * Marks this code as verified by emitting {@link VerificationCodeEvent.Verified}.
     *
     * <p>The application layer must handle that event and:
     * <ol>
     *   <li>Activate the {@link User} aggregate.</li>
     *   <li>Delete this code from the repository.</li>
     * </ol>
     *
     * @throws BusinessRuleException if the code has expired
     */
    public void verify() {
        if (isExpired()) throw new BusinessRuleException(VerificationCodeRule.ExpireAt.EXPIRED_MSG);

        this.events.add(new VerificationCodeEvent.Verified(this.id, this.userId, VerificationCodeRule.RedirectUrl.AFTER_VERIFICATION, this.code));
    }

    /**
     * Invalidates this code without verifying it, emitting
     * {@link VerificationCodeEvent.Invalidated}.
     *
     * <p>Typical use cases: the user requests a new code or an administrator
     * revokes it manually. The application layer must delete this code from
     * the repository in response to the event.
     */
    public void invalidate() {
        this.events.add(new VerificationCodeEvent.Invalidated(this.id, this.userId));
    }

    /**
     * Drains and returns all pending domain events.
     * Should be called by the repository after persisting the aggregate.
     */
    public @NonNull List<VerificationCodeEvent> pullEvents() {
        List<VerificationCodeEvent> snapshot = List.copyOf(this.events);
        this.events.clear();
        return snapshot;
    }

    /**
     * Returns a read-only view of pending events without clearing them.
     * Intended for testing and diagnostics.
     */
    public @NonNull List<VerificationCodeEvent> getEvents() {
        return Collections.unmodifiableList(this.events);
    }

    /**
     * Returns {@code true} if the current time is past {@link #expireAt}.
     */
    private boolean isExpired() {
        return Instant.now().isAfter(this.expireAt);
    }

    /**
     * Generates a cryptographically secure numeric code of exactly
     * {@link VerificationCodeRule.Code#LENGTH} digits using
     * {@link SecureRandom#nextLong(long)} for uniform distribution.
     */
    private static @NonNull String generateCode() {
        SecureRandom random = new SecureRandom();
        long min = (long) Math.pow(10, VerificationCodeRule.Code.LENGTH - 1);
        long max = (long) Math.pow(10, VerificationCodeRule.Code.LENGTH);
        return String.valueOf(min + random.nextLong(max - min));
    }
}