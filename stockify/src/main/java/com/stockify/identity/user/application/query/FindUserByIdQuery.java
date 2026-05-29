package com.stockify.identity.user.application.query;


import com.stockify.shared.vo.UserId;
import org.jmolecules.architecture.cqrs.QueryModel;
import org.jspecify.annotations.NonNull;

/**
 * Query that fetches a single user's data by their unique identifier.
 *
 * <p>Resolved by
 * {@link com.stockify.identity.user.application.usecase.FindUserByIdQueryHandler},
 * which delegates to the identity provider since user data is not stored
 * locally in Stockify.
 *
 * @param userId the identifier of the user to look up
 */
@QueryModel
public record FindUserByIdQuery(@NonNull UserId userId) {
}
