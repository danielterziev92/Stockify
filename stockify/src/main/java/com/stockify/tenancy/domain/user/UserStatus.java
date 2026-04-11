package com.stockify.tenancy.domain.user;

import com.stockify.shared.exception.BusinessRuleException;
import org.jmolecules.ddd.types.ValueObject;

/**
 * Represents the lifecycle status of a {@link User} account.
 *
 * <p>Each constant overrides {@link #requiredActive()} to enforce status-sensitive
 * guard logic inline — callers simply invoke {@code user.getStatus().requiredActive()}
 * without needing to branch on the status themselves.
 */
public enum UserStatus implements ValueObject {

    /**
     * The account is fully active and all operations are permitted.
     */
    ACTIVE {
        @Override
        public void requiredActive() {
        }
    },

    /**
     * The account has been deactivated; status-sensitive operations are blocked.
     */
    INACTIVE {
        @Override
        public void requiredActive() {
            throw new BusinessRuleException(UserRule.Status.INACTIVE_MSG);
        }
    },

    /**
     * The account is awaiting email verification; status-sensitive operations are blocked.
     */
    PENDING_VERIFICATION {
        @Override
        public void requiredActive() {
            throw new BusinessRuleException(UserRule.Status.PENDING_ACTIVATION_MSG);
        }
    };

    /**
     * Asserts that the account is in an {@link #ACTIVE} state.
     *
     * @throws BusinessRuleException if the account is not active
     */
    public abstract void requiredActive();
}
