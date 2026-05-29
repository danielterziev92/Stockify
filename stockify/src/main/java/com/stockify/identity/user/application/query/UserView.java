package com.stockify.identity.user.application.query;

import com.stockify.identity.user.domain.UserStatus;
import com.stockify.shared.vo.UserId;
import org.jmolecules.architecture.cqrs.QueryModel;
import org.jspecify.annotations.NonNull;

@QueryModel
public record UserView(
        @NonNull UserId userId,
        @NonNull String email,
        @NonNull UserStatus status
) {
}
