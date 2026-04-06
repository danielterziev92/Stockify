package com.stockify.user.domain.event;

import com.stockify.user.domain.vo.Email;
import com.stockify.user.domain.vo.UserId;
import com.stockify.user.domain.vo.VerificationCodeId;
import org.jmolecules.event.types.DomainEvent;
import org.jspecify.annotations.NonNull;

import java.time.Instant;

public sealed interface VerificationCodeEvent extends DomainEvent permits
        VerificationCodeEvent.Created,
        VerificationCodeEvent.Verified,
        VerificationCodeEvent.Deleted {

    @NonNull VerificationCodeId id();

    @NonNull UserId userId();

    @NonNull Instant occurredAt();

    record Created(
            @NonNull VerificationCodeId id,
            @NonNull UserId userId,
            @NonNull String code,
            @NonNull Instant expireAt,
            @NonNull String redirectUrl,
            @NonNull Email email,
            @NonNull Instant occurredAt
    ) implements VerificationCodeEvent {
        public Created(
                @NonNull VerificationCodeId id,
                @NonNull UserId userId,
                @NonNull String code,
                @NonNull Instant expireAt,
                @NonNull String redirectUrl,
                @NonNull Email email) {
            this(id, userId, code, expireAt, redirectUrl, email, Instant.now());
        }
    }

    record Verified(
            @NonNull VerificationCodeId id,
            @NonNull UserId userId,
            @NonNull String code,
            @NonNull Instant occurredAt
    ) implements VerificationCodeEvent {
        public Verified(@NonNull VerificationCodeId id, @NonNull UserId userId, @NonNull String code) {
            this(id, userId, code, Instant.now());
        }
    }

    record Deleted(
            @NonNull VerificationCodeId id,
            @NonNull UserId userId,
            @NonNull Instant occurredAt
    ) implements VerificationCodeEvent {
        public Deleted(@NonNull VerificationCodeId id, @NonNull UserId userId) {
            this(id, userId, Instant.now());
        }
    }
}
