package com.stockify.identity.otp.domain;

import com.stockify.shared.exception.BusinessRuleException;
import com.stockify.shared.vo.UserId;
import lombok.Getter;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jspecify.annotations.NonNull;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Aggregate root representing a one-time password (OTP) issued to a user.
 *
 * <p>An OTP is generated for a specific {@link OtpType} (e.g., email verification),
 * carries an expiry time, and can be verified exactly once via {@link #verify(String)}.
 *
 * <p><strong>Lifecycle &amp; invariant:</strong> at most one OTP exists per
 * {@code (userId, type)} pair (enforced by a unique constraint in persistence).
 * A successful {@link #verify(String)} is followed by deletion; an explicit resend
 * calls {@link #regenerate()} to overwrite the code in place rather than creating a
 * second row.
 *
 * <p>Use {@link #create} to issue a new OTP and {@link #reconstitute} to rehydrate
 * one from persisted state.
 */
@Getter
public class Otp implements AggregateRoot<Otp, OtpId> {

    private final OtpId id;
    private final UserId userId;
    private final OtpType type;

    /**
     * Mutable so {@link #regenerate()} can replace it while keeping the same identity.
     */
    private String code;

    /**
     * Mutable so {@link #regenerate()} can extend it while keeping the same identity.
     */
    private Instant expiresAt;

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
     * Generates a new OTP for the given user and purpose, and publishes an
     * {@link OtpEvent.Created} event carrying the freshly generated code.
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
        otp.events.add(new OtpEvent.Created(otpId, userId, type, code));

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
     * Regenerates this OTP in place: assigns a fresh code and new expiry derived
     * from {@link OtpType#expiry()}, while preserving {@link #getId() id},
     * {@link #getUserId() userId} and {@link #getType() type}. Publishes an
     * {@link OtpEvent.Reissued} event carrying the new code.
     *
     * <p>Keeping the identity stable lets the persistence layer update the existing
     * row instead of inserting a duplicate.
     */
    public void regenerate() {
        this.code = generateCode();
        this.expiresAt = Instant.now().plus(this.type.expiry());
        this.events.add(new OtpEvent.Reissued(this.id, this.userId, this.type, this.code));
    }

    /**
     * Verifies the submitted code against this OTP and publishes an
     * {@link OtpEvent.Verified} event on success.
     *
     * <p>Performs both guards inside the aggregate: expiry first, then a
     * constant-time comparison of the submitted code against the stored one.
     * After this method returns, the caller must delete the OTP from persistence.
     *
     * @param submittedCode the code submitted by the user
     * @throws BusinessRuleException if the OTP has expired
     *                               ({@link OtpRule.Expiry#EXPIRED_MSG}) or the
     *                               submitted code does not match
     *                               ({@link OtpRule.Code#INVALID_MSG})
     */
    public void verify(@NonNull String submittedCode) {
        if (Instant.now().isAfter(this.expiresAt))
            throw new BusinessRuleException(OtpRule.Expiry.EXPIRED_MSG);

        if (!matches(submittedCode))
            throw new BusinessRuleException(OtpRule.Code.INVALID_MSG);

        this.events.add(new OtpEvent.Verified(this.id, this.userId, this.type));
    }

    /**
     * Returns all pending domain events and clears the internal event list.
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
     * @return an immutable view of the current pending events
     */
    public @NonNull List<OtpEvent> getEvents() {
        return List.copyOf(this.events);
    }

    /**
     * Generates a zero-padded {@link OtpRule.Code#LENGTH}-digit numeric code
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

    /**
     * Constant-time comparison of the submitted code against the stored code,
     * to avoid leaking information through timing differences.
     *
     * @param submittedCode the code submitted by the user
     * @return {@code true} if the codes are byte-for-byte equal
     */
    private boolean matches(@NonNull String submittedCode) {
        return MessageDigest.isEqual(
                this.code.getBytes(StandardCharsets.UTF_8),
                submittedCode.getBytes(StandardCharsets.UTF_8)
        );
    }
}
