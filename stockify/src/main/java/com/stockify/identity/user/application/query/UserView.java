package com.stockify.identity.user.application.query;

import com.stockify.identity.user.domain.UserStatus;
import com.stockify.shared.vo.UserId;
import org.jspecify.annotations.NonNull;

public record UserView(
        @NonNull UserId userId,
        @NonNull String email,
        @NonNull UserStatus status
) {
}
