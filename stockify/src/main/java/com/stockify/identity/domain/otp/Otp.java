package com.stockify.identity.domain.otp;

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
 * Aggregate root representing a one-time password (OTP) issued to a user.
 *
 * <p>An OTP is generated for a specific {@link OtpType} (e.g. email verification),
 * carries an expiry time, and can be verified exactly once via {@link #verify()}.
 *
 * <p>Verified and expired OTPs are deleted from persistence immediately — their
 * absence in the repository implicitly signals that verification has already
 * occurred or the code has expired.
 *
 * <p>Use {@link #create} to issue a new OTP and {@link #reconstitute} to rehydrate
 * one from persisted state.
 */
@Getter
public class Otp implements AggregateRoot<Otp, OtpId> {

    private final OtpId id;
    private final UserId userId;
    private final OtpType type;
    private final String code;
    private final Instant expiresAt;

    private final List<OtpEvent> events;

    private Otp(
            @NonNull OtpId id,
            @NonNull UserId userId,
            @NonNull OtpType type,
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
     * Generates a new OTP for the given user and purpose, and publishes
     * an {@link OtpEvent.Created} event.
     *
     * <p>The code is an {@link OtpRule.Code#LENGTH}-digit zero-padded numeric string
     * generated with a {@link SecureRandom}. The expiry time is derived from
     * {@link OtpType#expiry()}.
     *
     * @param userId the identifier of the user this OTP is issued for
     * @param type   the purpose for which the OTP is generated
     * @return a new {@code Otp} with one pending {@link OtpEvent.Created} event
     */
    public static @NonNull Otp create(@NonNull UserId userId, @NonNull OtpType type) {
        OtpId otpId = OtpId.generate();
        String code = generateCode();
        Instant expiresAt = Instant.now().plus(type.expiry());

        Otp otp = new Otp(otpId, userId, type, code, expiresAt);
        otp.events.add(new OtpEvent.Created(otpId, userId, type));

        return otp;
    }

    /**
     * Rebuilds an {@code Otp} from persisted state without publishing any events.
     *
     * @param id        the persisted OTP identifier
     * @param userId    the identifier of the owning user
     * @param type      the purpose for which the OTP was originally generated
     * @param code      the persisted numeric code
     * @param expiresAt the persisted expiry instant
     * @return an {@code Otp} reflecting the stored state
     */
    public static @NonNull Otp reconstitute(
            @NonNull OtpId id,
            @NonNull UserId userId,
            @NonNull OtpType type,
            @NonNull String code,
            @NonNull Instant expiresAt
    ) {
        return new Otp(id, userId, type, code, expiresAt);
    }

    /**
     * Verifies this OTP and publishes an {@link OtpEvent.Verified} event.
     *
     * <p>The caller is responsible for matching the submitted code against
     * {@link #getCode()} before invoking this method — code matching is an
     * infrastructure concern handled by the repository lookup.
     *
     * <p>After this method returns, the caller must delete the OTP from persistence.
     * Its absence in the repository signals that verification has already occurred.
     *
     * @throws BusinessRuleException if the OTP has expired
     */
    public void verify() {
        if (Instant.now().isAfter(this.expiresAt))
            throw new BusinessRuleException(OtpRule.Expiry.EXPIRED_MSG);

        this.events.add(new OtpEvent.Verified(this.id, this.userId, this.type));
    }

    /**
     * Returns all pending domain events and clears the internal event list.
     *
     * <p>Callers are responsible for publishing the returned events before
     * the aggregate is persisted.
     *
     * @return an immutable snapshot of the pending events
     */
    public @NonNull List<OtpEvent> pullEvents() {
        List<OtpEvent> snapshot = List.copyOf(this.events);
        this.events.clear();
        return snapshot;
    }

    /**
     * Returns an immutable snapshot of the pending domain events without clearing them.
     *
     * <p>Use {@link #pullEvents()} instead when you want to consume and clear
     * the events in a single operation.
     *
     * @return an immutable view of the current pending events
     */
    public @NonNull List<OtpEvent> getEvents() {
        return List.copyOf(this.events);
    }

    /**
     * Generates a zero-padded {@link OtpRule.Code#LENGTH}-digit numeric OTP code
     * using a cryptographically strong random number generator.
     *
     * @return a zero-padded numeric string of exactly {@link OtpRule.Code#LENGTH} digits
     */
    private static @NonNull String generateCode() {
        return String.format(
                "%0" + OtpRule.Code.LENGTH + "d",
                new SecureRandom().nextInt(OtpRule.Code.UPPER_BOUND)
        );
    }
}
