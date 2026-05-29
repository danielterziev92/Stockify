package com.stockify.identity.otp.application.usecase;

import com.stockify.identity.otp.application.OtpLookupService;
import com.stockify.identity.otp.application.command.ResendOtpCommand;
import com.stockify.identity.otp.domain.Otp;
import com.stockify.identity.otp.domain.OtpRepository;
import com.stockify.shared.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.cqrs.CommandHandler;
import org.jspecify.annotations.NonNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handles an explicit request to resend a one-time password.
 *
 * <p>Looks up the existing OTP for the user, generates a new code, updates
 * the persisted record, and publishes the domain event so the email
 * notification is dispatched.
 */
@Service
@RequiredArgsConstructor
public class ResendOtpUseCase {

    private final OtpRepository otpRepository;
    private final OtpLookupService service;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Executes the resend OTP flow.
     *
     * <ol>
     *   <li>Resolves the user ID from the identity provider by email.</li>
     *   <li>Looks up the existing OTP by user ID and type.</li>
     *   <li>Generates a new {@link Otp} with a fresh code and expiry.</li>
     *   <li>Updates the persisted record.</li>
     *   <li>Publishes domain events so the email notification is dispatched.</li>
     * </ol>
     *
     * @param command the resend input; must not be {@code null}
     * @throws EntityNotFoundException if no OTP record exists for the given email
     */
    @CommandHandler
    @Transactional
    public void handle(@NonNull ResendOtpCommand command) {
        Otp otp = this.service.getEmailVerificationOtpByEmail(command.email());

        otp.regenerate();
        this.otpRepository.update(otp);

        otp.pullEvents().forEach(this.eventPublisher::publishEvent);
    }
}
