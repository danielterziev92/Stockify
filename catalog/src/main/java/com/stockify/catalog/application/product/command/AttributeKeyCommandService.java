package com.stockify.catalog.application.product.command;

import com.stockify.catalog.domain.product.AttributeKey;
import com.stockify.catalog.domain.product.repository.AttributeKeyRepository;
import com.stockify.catalog.domain.product.rule.AttributeRule;
import com.stockify.catalog.domain.product.vo.AttributeKeyId;
import com.stockify.catalog.domain.product.vo.AttributeKeyName;
import com.stockify.catalog.domain.product.vo.AttributeValueId;
import com.stockify.catalog.shared.event.DomainEventPublisher;
import com.stockify.catalog.shared.exception.BusinessRuleException;
import com.stockify.catalog.shared.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttributeKeyCommandService {

    private final AttributeKeyRepository repository;
    private final DomainEventPublisher publisher;

    public @NonNull UUID handle(AttributeKeyCommand.@NonNull CreateKey command) {
        if (this.repository.existsByName(command.name()))
            throw new BusinessRuleException(AttributeRule.AttributeKey.Name.DUPLICATE_MSG, command.name());

        AttributeKey key = AttributeKey.create(new AttributeKeyName(command.name()));
        this.save(key);

        return key.getId().value();
    }

    public void handle(AttributeKeyCommand.@NonNull RenameKey command) {
        AttributeKey key = this.load(command.keyId());

        if (this.repository.existsByName(command.newName()))
            throw new BusinessRuleException(AttributeRule.AttributeKey.Name.DUPLICATE_MSG, command.newName());

        key.rename(new AttributeKeyName(command.newName()));
        this.save(key);
    }

    public @NonNull UUID handle(AttributeKeyCommand.@NonNull AddValue command) {
        AttributeKey key = this.load(command.keyId());

        AttributeValueId valueId = key.addValue(command.value(), command.abbreviation());
        this.save(key);

        return valueId.value();
    }

    public void handle(AttributeKeyCommand.@NonNull RemoveValue command) {
        AttributeKey key = this.load(command.keyId());
        key.removeValue(AttributeValueId.of(command.valueId()));
        this.save(key);
    }

    public void handle(AttributeKeyCommand.@NonNull ReplaceValue command) {
        AttributeKey key = this.load(command.keyId());

        AttributeValueId newValueId = key.replaceValue(
                AttributeValueId.of(command.oldValueId()),
                command.newValue(),
                command.newAbbreviation()
        );

        this.save(key);
    }

    private void save(@NonNull AttributeKey attributeKey) {
        this.repository.save(attributeKey);
        this.publisher.publish(attributeKey.domainEvents());
        attributeKey.clearEvents();
    }

    public @NonNull AttributeKey load(@NonNull UUID id) {
        return this.repository.findById(AttributeKeyId.of(id))
                .orElseThrow(
                        () -> new EntityNotFoundException(AttributeRule.AttributeKey.Generic.NOT_FOUND_MSG, id)
                );
    }
}
