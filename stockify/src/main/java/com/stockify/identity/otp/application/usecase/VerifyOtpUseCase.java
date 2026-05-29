package com.stockify.identity.otp.application.usecase;


import com.stockify.identity.otp.domain.*;
import com.stockify.identity.otp.application.command.VerifyOtpCommand;
import com.stockify.identity.user.application.port.IdentityProviderPort;
import com.stockify.identity.user.domain.Email;
import com.stockify.shared.exception.BusinessRuleException;
import com.stockify.shared.exception.EntityNotFoundException;
import com.stockify.shared.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.cqrs.CommandHandler;
import org.jspecify.annotations.NonNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handles the verification of a one-time password submitted by the user.
 *
 * <p>If the OTP has expired, a new one is generated, persisted, and its
 * {@link OtpEvent.Created} event is published
 * so the email notification is dispatched automatically. An exception is then
 * thrown to inform the caller that a fresh code has been sent.
 *
 * <p>On successful verification the OTP is deleted, the user's email is marked
 * as verified in the identity provider, and the domain events are published.
 */
@Service
@RequiredArgsConstructor
public class VerifyOtpUseCase {

    private final OtpRepository otpRepository;
    private final IdentityProviderPort identityProvider;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Executes the OTP verification flow.
     *
     * <ol>
     *   <li>Looks up the OTP by the submitted code.</li>
     *   <li>If the OTP has expired, generates a new one, persists it, publishes
     *       its events, and throws a {@link BusinessRuleException} to inform the caller.</li>
     *   <li>Calls {@link Otp#verify(String code)} to record the verified event.</li>
     *   <li>Deletes the verified OTP from persistence.</li>
     *   <li>Marks the user's email as verified in the identity provider.</li>
     *   <li>Publishes all pending domain events.</li>
     * </ol>
     *
     * @param command the verification input; must not be {@code null}
     * @throws EntityNotFoundException if no OTP matches the submitted code
     * @throws BusinessRuleException   if the OTP has expired; a new code is
     *                                 generated and sent automatically
     */
    @CommandHandler
    @Transactional
    public void handle(@NonNull VerifyOtpCommand command) {
        Email email = new Email(command.email());
        UserId userId = this.identityProvider.findUserIdByEmail(email.value());

        Otp otp = this.otpRepository.findByUserIdAndType(userId, OtpType.EMAIL_VERIFICATION)
                .orElseThrow(() -> new EntityNotFoundException(OtpRule.Code.NOT_FOUND_MSG, email.value()));

        otp.verify(command.code());

        this.otpRepository.deleteById(otp.getId());
        this.identityProvider.verifyUserEmail(otp.getUserId());

        otp.pullEvents().forEach(this.eventPublisher::publishEvent);
    }
}
