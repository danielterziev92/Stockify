package com.stockify.user.domain;

import com.stockify.shared.exception.BusinessRuleException;
import com.stockify.user.domain.event.VerificationCodeEvent;
import com.stockify.user.domain.rule.VerificationCodeRule;
import com.stockify.user.domain.vo.Email;
import com.stockify.user.domain.vo.UserId;
import com.stockify.user.domain.vo.VerificationCodeId;
import jakarta.annotation.Nonnull;
import lombok.Builder;
import lombok.Getter;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
public class VerificationCode implements AggregateRoot<VerificationCode, VerificationCodeId> {

    private final VerificationCodeId id;
    private final UserId userId;
    private final String code;
    private final Instant expireAt;
    private Instant deletedAt;
    private final String redirectUrl;

    private final List<VerificationCodeEvent> events;

    @Builder
    private VerificationCode(
            VerificationCodeId id,
            UserId userId,
            String code,
            Instant expireAt,
            Instant deletedAt,
            String redirectUrl
    ) {
        this.id = id;
        this.userId = userId;
        this.code = code;
        this.expireAt = expireAt;
        this.deletedAt = deletedAt;
        this.redirectUrl = redirectUrl;
        this.events = new ArrayList<>();
    }

    public static @NonNull VerificationCode generate(@NonNull UserId userId, @NonNull Email email) {
        VerificationCode verificationCode = VerificationCode.builder()
                .id(VerificationCodeId.generate())
                .userId(userId)
                .code(generateCode())
                .expireAt(Instant.now().plusSeconds(VerificationCodeRule.ExpireAt.DURATION_IN_SECONDS))
                .redirectUrl(VerificationCodeRule.RedirectUrl.AFTER_VERIFICATION)
                .build();

        verificationCode.events.add(new VerificationCodeEvent.Created(
                verificationCode.getId(),
                verificationCode.getUserId(),
                verificationCode.getCode(),
                verificationCode.getExpireAt(),
                verificationCode.getRedirectUrl(),
                email
        ));

        return verificationCode;
    }

    public static @Nonnull VerificationCode reconstitute(
            @NonNull VerificationCodeId id,
            @NonNull UserId userId,
            @NonNull String code,
            @NonNull Instant expireAt,
            @Nullable Instant deletedAt,
            @NonNull String redirectUrl
    ) {
        return new VerificationCode(id, userId, code, expireAt, deletedAt, redirectUrl);
    }

    public void verify() {
        if (isExpired()) throw new BusinessRuleException(VerificationCodeRule.ExpireAt.EXPIRE_ALREADY_MSG);

        this.events.add(new VerificationCodeEvent.Verified(this.id, this.userId, this.code));
    }

    public void delete() {
        this.deletedAt = Instant.now();
        this.events.add(new VerificationCodeEvent.Deleted(this.id, this.userId));
    }

    public @NonNull VerificationCode reGenerate(@NonNull UserId userId, @NonNull Email email) {
        delete();
        return generate(userId, email);
    }

    private boolean isExpired() {
        return Instant.now().isAfter(this.expireAt);
    }

    private static @NonNull String generateCode() {
        SecureRandom random = new SecureRandom();

        long min = (long) Math.pow(10, VerificationCodeRule.Code.LENGTH - 1);
        long max = (long) Math.pow(10, VerificationCodeRule.Code.LENGTH) - 1;

        long code = min + (long) (random.nextDouble() * (max - min + 1));
        return String.valueOf(code);
    }
}