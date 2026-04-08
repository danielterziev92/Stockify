package com.stockify.user.domain;

import com.stockify.shared.exception.BusinessRuleException;
import com.stockify.user.domain.rule.UserRule;

public enum UserStatus {

    /**
     * The account is active and the user has full access to the system.
     */
    ACTIVE {
        @Override
        public void requiredActive() {
        }
    },

    /**
     * The account is temporarily locked after multiple failed login attempts.
     * Unlocks automatically after the lockout period expires or by an administrator.
     */
    LOCKED {
        @Override
        public void requiredActive() {
            throw new BusinessRuleException(UserRule.Status.LOCKED_MSG);
        }
    },

    /**
     * A newly created account awaiting email confirmation or administrator approval.
     */
    PENDING_ACTIVATION {
        @Override
        public void requiredActive() {
            throw new BusinessRuleException(UserRule.Status.PENDING_ACTIVATION_MSG);
        }
    };

    public abstract void requiredActive();
}