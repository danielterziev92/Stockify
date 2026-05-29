package com.stockify.identity.user.application.query;


import com.stockify.shared.vo.UserId;
import org.jmolecules.architecture.cqrs.QueryModel;
import org.jspecify.annotations.NonNull;

@QueryModel
public record FindUserByIdQuery(@NonNull UserId userId) {
}
