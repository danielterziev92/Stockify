package com.stockify.identity.otp.application;

import com.stockify.identity.otp.domain.Otp;
import com.stockify.identity.otp.domain.OtpRepository;
import com.stockify.identity.otp.domain.OtpRule;
import com.stockify.identity.otp.domain.OtpType;
import com.stockify.identity.user.application.port.IdentityProviderPort;
import com.stockify.identity.user.domain.Email;
import com.stockify.shared.exception.EntityNotFoundException;
import com.stockify.shared.vo.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;

/**
 * Domain service providing OTP lookup operations shared across multiple use cases.
 *
 * <p>Centralizes the two-step resolution pattern — email → userId via the
 * identity provider, then userId + type → {@link Otp} via the repository —
 * so individual use cases stay focused on their own business logic.
 *
 * <p>This service is intentionally stateless and read-only; it never mutates
 * an {@link Otp} or publishes domain events.
 */
@Slf4j
@org.springframework.stereotype.Service
@org.jmolecules.ddd.annotation.Service
@RequiredArgsConstructor
public class OtpLookupService {

    private final OtpRepository repository;
    private final IdentityProviderPort identityProvider;

    /**
     * Resolves the active email-verification {@link Otp} for the given email address.
     *
     * <p>Performs a two-step lookup:
     * <ol>
     *   <li>Resolves the {@link UserId} from the identity provider by the raw email.</li>
     *   <li>Fetches the {@link Otp} of type {@link OtpType#EMAIL_VERIFICATION}
     *       for that user from the repository.</li>
     * </ol>
     *
     * @param rawEmail the raw email address of the user; must not be {@code null}
     * @return the active email-verification OTP for the resolved user
     * @throws com.stockify.shared.exception.InvalidValueException if {@code rawEmail}
     *         violates the {@link Email} domain rules
     * @throws com.stockify.shared.exception.EntityNotFoundException if no user exists
     *         with the given email, or no active email-verification OTP exists for that user
     */
    public Otp getEmailVerificationOtpByEmail(@NonNull String rawEmail) {
        log.debug("Resolving email-verification OTP for email '{}'", rawEmail);

        Email email = new Email(rawEmail);
        UserId userId = this.identityProvider.findUserIdByEmail(email.value());

        log.debug("Identity provider resolved userId '{}' for email '{}'", userId.value(), email.value());

        Otp otp = this.repository.findByUserIdAndType(userId, OtpType.EMAIL_VERIFICATION)
                .orElseThrow(() -> {
                    log.warn("No active email-verification OTP found for userId '{}'", userId.value());
                    return new EntityNotFoundException(OtpRule.Code.NOT_FOUND_MSG, rawEmail);
                });

        log.debug("Email-verification OTP '{}' resolved for userId '{}'", otp.getId().value(), userId.value());

        return otp;
    }
}
