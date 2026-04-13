package com.stockify.identity.domain.verificationcode;

import com.stockify.shared.exception.BusinessRuleException;
import com.stockify.shared.vo.UserId;
import lombok.Getter;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jspecify.annotations.NonNull;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Aggregate root representing a one-time verification code issued to a user.
 * <p>
 * A code is generated for a specific {@link VerificationCodeType}, carries its own
 * expiry instant, and becomes invalid after {@link #verify()} is called or the
 * expiry time is exceeded. Every state change produces a {@link VerificationCodeEvent}
 * that can be drained via {@link #pullEvents()} for publication.
 */
@Getter
public class VerificationCode implements AggregateRoot<VerificationCode, VerificationCodeId> {

    private final VerificationCodeId id;
    private final UserId userId;
    private final VerificationCodeType type;
    private final String code;
    private final Instant expiresAt;

    private final List<VerificationCodeEvent> events;

    private VerificationCode(
            @NonNull VerificationCodeId id,
            @NonNull UserId userId,
            @NonNull VerificationCodeType type,
            @NonNull String code,
            @NonNull Instant expiresAt
    ) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.code = code;
        this.expiresAt = expiresAt;
        this.events = new ArrayList<>(2);
    }

    /**
     * Creates a new {@link VerificationCode} for the given user and type.
     * <p>
     * Generates a random {@link VerificationCodeRule.Code#LENGTH}-digit code, resolves the
     * redirect URL and expiry from the type, and records a {@link VerificationCodeEvent.Created} event.
     *
     * @param userId the user the code is issued for
     * @param type   the purpose of the code
     * @return the newly created verification code, never {@code null}
     */
    public static @NonNull VerificationCode create(@NonNull UserId userId, @NonNull VerificationCodeType type) {
        VerificationCodeId id = VerificationCodeId.generate();
        String code = generateCode();
        Instant expiresAt = Instant.now().plus(type.expiry());

        VerificationCode verificationCode = new VerificationCode(id, userId, type, code, expiresAt);
        verificationCode.events.add(new VerificationCodeEvent.Created(id, userId, type, code, expiresAt));

        return verificationCode;
    }

    /**
     * Reconstitutes an existing {@link VerificationCode} from persistent state without
     * raising any domain events.
     * <p>
     * Use this factory when rehydrating from storage rather than issuing a new code.
     *
     * @param id          the persisted identifier
     * @param userId      the owning user
     * @param type        the purpose of the code
     * @param code        the persisted code value
     * @param expiresAt   the persisted expiry instant
     * @return the reconstituted verification code, never {@code null}
     */
    public static @NonNull VerificationCode reconstitute(
            @NonNull VerificationCodeId id,
            @NonNull UserId userId,
            @NonNull VerificationCodeType type,
            @NonNull String code,
            @NonNull Instant expiresAt
    ) {
        return new VerificationCode(id, userId, type, code, expiresAt);
    }

    /**
     * Marks this verification code as redeemed and records a {@link VerificationCodeEvent.Verified} event.
     *
     * @throws com.stockify.shared.exception.BusinessRuleException if the code has expired
     */
    public void verify() {
        if (Instant.now().isAfter(this.expiresAt))
            throw new BusinessRuleException(VerificationCodeRule.Code.EXPIRED_MSG);

        this.events.add(new VerificationCodeEvent.Verified(this.id, this.userId, this.type));
    }

    /**
     * Returns a snapshot of all accumulated events and clears the internal event list.
     *
     * @return an immutable copy of the pending events, never {@code null}
     */
    public @NonNull List<VerificationCodeEvent> pullEvents() {
        List<VerificationCodeEvent> snapshot = List.copyOf(this.events);
        this.events.clear();
        return snapshot;
    }

    /**
     * Returns a read-only view of the currently accumulated events without clearing them.
     *
     * @return an immutable copy of the pending events, never {@code null}
     */
    public @NonNull List<VerificationCodeEvent> getEvents() {
        return List.copyOf(this.events);
    }

    /**
     * Generates a zero-padded {@link VerificationCodeRule.Code#LENGTH}-digit numeric code using a {@link SecureRandom}.
     */
    private static @NonNull String generateCode() {
        return String.format(
                "%0" + VerificationCodeRule.Code.LENGTH + "d",
                new SecureRandom().nextInt(1_000_000)
        );
    }
}
