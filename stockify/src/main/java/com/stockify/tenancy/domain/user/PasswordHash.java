package com.stockify.tenancy.domain.user;

import com.stockify.shared.exception.BusinessRuleException;
import org.jmolecules.ddd.types.ValueObject;
import org.jspecify.annotations.NonNull;

/**
 * Value object representing a valid BCrypt password hash.
 *
 * <p>On construction the value is validated against the expected BCrypt format:
 * it must not be blank, must be exactly {@link UserRule.PasswordHash#BCRYPT_LENGTH}
 * characters long, and must match {@link UserRule.PasswordHash#BCRYPT_PATTERN}.
 * Any violation throws a {@link BusinessRuleException} with the appropriate message code.
 *
 * @param value the raw BCrypt hash string
 */
public record PasswordHash(@NonNull String value) implements ValueObject {

    /**
     * Compact constructor — validates that the value is a well-formed BCrypt hash.
     *
     * @throws BusinessRuleException if {@code value} is blank, does not equal
     *                               {@link UserRule.PasswordHash#BCRYPT_LENGTH} characters,
     *                               or does not match {@link UserRule.PasswordHash#BCRYPT_PATTERN}
     */
    public PasswordHash {
        if (value.isBlank()) throw new BusinessRuleException(UserRule.PasswordHash.BLANK_MSG);
        if (value.length() != UserRule.PasswordHash.BCRYPT_LENGTH)
            throw new BusinessRuleException(UserRule.PasswordHash.BCRYPT_LENGTH_MSG, value.length());
        if (!UserRule.PasswordHash.BCRYPT_PATTERN.matcher(value).matches())
            throw new BusinessRuleException(UserRule.PasswordHash.BCRYPT_PATTERN_MSG, value);
    }

    /**
     * Factory method for creating a {@code PasswordHash} from a raw string.
     *
     * @param value the raw BCrypt hash input
     * @return a new, validated {@code PasswordHash} instance
     */
    public @NonNull PasswordHash of(@NonNull String value) {
        return new PasswordHash(value);
    }
}
