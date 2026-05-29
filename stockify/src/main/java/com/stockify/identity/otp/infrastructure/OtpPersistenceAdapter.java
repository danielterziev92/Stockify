package com.stockify.identity.otp.infrastructure;

import com.stockify.identity.otp.domain.Otp;
import com.stockify.identity.otp.domain.OtpId;
import com.stockify.identity.otp.domain.OtpRepository;
import com.stockify.identity.otp.domain.OtpType;
import com.stockify.shared.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Spring Data JDBC adapter implementing the {@link OtpRepository} port.
 *
 * <p>Translates between the {@link Otp} aggregate (value objects) and
 * {@link OtpRecord} (primitive columns), and resolves the insert-vs-update
 * ambiguity of assigned-id aggregates by routing through
 * {@link JdbcAggregateTemplate} after an existence check.
 */
@Component
@RequiredArgsConstructor
class OtpPersistenceAdapter implements OtpRepository {

    private final OtpJdbcRepository store;
    private final JdbcAggregateTemplate template;

    @Override
    public Optional<Otp> findByUserIdAndType(@NonNull UserId userId, @NonNull OtpType type) {
        return this.store.findByUserIdAndType(userId.value(), type.name())
                .map(this::toDomain);
    }

    /**
     * Inserts a new OTP row or updates the existing one with the same id.
     *
     * <p>Spring Data JDBC cannot infer new-vs-existing for an aggregate whose id is
     * always assigned, so we decide explicitly: an existence check selects between
     * {@link JdbcAggregateTemplate#insert} and {@link JdbcAggregateTemplate#update}.
     */
    @Override
    public void save(@NonNull Otp otp) {
        OtpRecord record = toRecord(otp);
        if (this.store.existsById(record.id())) {
            this.template.update(record);
        } else {
            this.template.insert(record);
        }
    }

    @Override
    public void deleteById(@NonNull OtpId id) {
        this.store.deleteById(id.value());
    }

    private OtpRecord toRecord(Otp otp) {
        return new OtpRecord(
                otp.getId().value(),
                otp.getUserId().value(),
                otp.getType().name(),
                otp.getCode(),
                otp.getExpiresAt()
        );
    }

    private Otp toDomain(OtpRecord record) {
        return Otp.reconstitute(
                OtpId.of(record.id()),
                UserId.of(record.userId()),
                OtpType.valueOf(record.type()),
                record.code(),
                record.expiresAt()
        );
    }
}
