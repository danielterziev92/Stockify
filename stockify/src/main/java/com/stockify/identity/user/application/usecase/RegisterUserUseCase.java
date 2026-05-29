package com.stockify.identity.user.application.usecase;

import com.stockify.identity.domain.otp.Otp;
import com.stockify.identity.domain.otp.OtpRepository;
import com.stockify.identity.domain.otp.OtpType;
import com.stockify.identity.user.application.command.RegisterUserCommand;
import com.stockify.identity.user.application.port.IdentityProviderPort;
import com.stockify.identity.user.domain.Email;
import com.stockify.identity.user.domain.Password;
import com.stockify.identity.user.domain.User;
import com.stockify.shared.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handles the registration of a new user account.
 *
 * <p>Validates the provided credentials, creates the user in the identity provider,
 * constructs the domain object, and initiates the email verification flow by
 * generating and persisting an OTP.
 *
 * <p>If the identity provider returns an error after validation, the exception
 * is propagated to the caller — no partial state is left in Stockify.
 */
@Service
@RequiredArgsConstructor
public class RegisterUserUseCase {

    private final IdentityProviderPort identityProvider;
    private final OtpRepository otpRepository;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Executes the registration flow for a new user.
     *
     * <ol>
     *   <li>Validates {@code email} and {@code password} via domain value objects.</li>
     *   <li>Creates the user in the identity provider; propagates any error to the caller.</li>
     *   <li>Constructs the {@link User} domain object with the assigned ID.</li>
     *   <li>Generates an {@link Otp} for email verification and persists it.</li>
     *   <li>Publishes domain events so the email notification is dispatched.</li>
     * </ol>
     *
     * @param command the registration input; must not be {@code null}
     * @throws com.stockify.shared.exception.InvalidValueException if the email or
     *                                                             password violates domain rules
     * @throws com.stockify.shared.exception.BusinessRuleException if the email
     *                                                             is already registered
     */
    @Transactional
    public void execute(@NonNull RegisterUserCommand command) {
        Email email = new Email(command.email());
        Password password = new Password(command.password());

        UserId userId = this.identityProvider.createUser(email.value(), password.value());

        User user = User.create(userId, email, password);
        user.pullEvents().forEach(this.eventPublisher::publishEvent);

        Otp otp = Otp.create(userId, OtpType.EMAIL_VERIFICATION);
        this.otpRepository.save(otp);
        otp.pullEvents().forEach(this.eventPublisher::publishEvent);
    }
}
