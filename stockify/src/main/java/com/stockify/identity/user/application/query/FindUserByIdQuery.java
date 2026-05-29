package com.stockify.identity.user.application.query;


import com.stockify.shared.vo.UserId;
import org.jspecify.annotations.NonNull;

public record FindUserByIdQuery(@NonNull UserId userId) {
}
