package com.stockify.user.domain.vo;

import lombok.NonNull;
import org.jmolecules.ddd.types.Identifier;

import java.util.UUID;

public record VerificationCodeId(@NonNull UUID value) implements Identifier {

    public static @NonNull VerificationCodeId generate() {
        return new VerificationCodeId(UUID.randomUUID());
    }

    public static @NonNull VerificationCodeId of(@NonNull UUID value) {
        return new VerificationCodeId(value);
    }

    public static @NonNull VerificationCodeId of(@NonNull String value) {
        return new VerificationCodeId(UUID.fromString(value));
    }
}
