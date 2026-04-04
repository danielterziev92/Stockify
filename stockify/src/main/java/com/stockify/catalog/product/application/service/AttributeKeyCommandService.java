package com.stockify.catalog.product.application.service;

import com.stockify.catalog.exception.EntityNotFoundException;
import com.stockify.catalog.product.application.command.AttributeKeyCommand;
import com.stockify.catalog.product.application.dto.AttributeKeyDto;
import com.stockify.catalog.product.application.mapper.AttributeKeyMapper;
import com.stockify.catalog.product.domain.AttributeKey;
import com.stockify.catalog.product.domain.AttributeValue;
import com.stockify.catalog.product.domain.repository.AttributeKeyRepository;
import com.stockify.catalog.product.domain.rule.AttributeRule;
import com.stockify.catalog.product.domain.vo.AttributeKeyId;
import com.stockify.catalog.product.domain.vo.AttributeValueId;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttributeKeyCommandService {

    private final AttributeKeyRepository repository;
    private final AttributeKeyMapper mapper;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public @NonNull AttributeKeyDto create(AttributeKeyCommand.@NonNull CreateKey command) {
        AttributeKey key = AttributeKey.create(command.name());

        this.repository.save(key);
        this.publishEvents(key);

        return this.mapper.toDto(key);
    }

    @Transactional
    public @NonNull AttributeKeyDto createWithValues(AttributeKeyCommand.@NonNull CreateKeyWithValues command) {
        List<AttributeValue> values = command.values().stream()
                .map(input -> AttributeValue.create(input.value(), input.abbreviation()))
                .toList();

        AttributeKey key = AttributeKey.create(command.name());
        values.forEach(key::addValue);

        this.repository.save(key);
        this.publishEvents(key);

        return this.mapper.toDto(key);
    }

    @Transactional
    public @NonNull AttributeKeyDto rename(AttributeKeyCommand.@NonNull RenameKey command) {
        AttributeKey key = this.findKeyById(command.id());

        key.rename(command.newName());
        this.repository.save(key);
        this.publishEvents(key);

        return this.mapper.toDto(key);
    }

    @Transactional
    public @NonNull AttributeKeyDto addValue(AttributeKeyCommand.@NonNull AddValue command) {
        AttributeKey key = this.findKeyById(command.keyId());

        key.addValue(command.value(), command.abbreviation());
        this.repository.save(key);
        this.publishEvents(key);

        return this.mapper.toDto(key);
    }

    @Transactional
    public @NonNull AttributeKeyDto replaceValue(AttributeKeyCommand.@NonNull ReplaceValue command) {
        AttributeKey key = this.findKeyById(command.keyId());

        AttributeValue oldValue = this.findValueById(key, command.oldValueId());
        AttributeValue newValue = this.findValueById(key, command.newValueId());

        key.replaceValue(oldValue, newValue);
        this.repository.save(key);
        this.publishEvents(key);

        return this.mapper.toDto(key);
    }

    @Transactional
    public @NonNull AttributeKeyDto removeValue(AttributeKeyCommand.@NonNull RemoveValue command) {
        AttributeKey key = this.findKeyById(command.keyId());
        AttributeValue value = this.findValueById(key, command.valueId());

        key.removeValue(value);
        this.repository.save(key);
        this.publishEvents(key);

        return this.mapper.toDto(key);
    }

    @Transactional
    public @NonNull AttributeKeyDto changeValue(AttributeKeyCommand.@NonNull ChangeValue command) {
        AttributeKey key = this.findKeyById(command.keyId());
        AttributeValue value = this.findValueById(key, command.valueId());

        key.changeValue(value, command.newValue());
        this.repository.save(key);
        this.publishEvents(key);

        return this.mapper.toDto(key);
    }

    @Transactional
    public @NonNull AttributeKeyDto changeAbbreviation(AttributeKeyCommand.@NonNull ChangeAbbreviation command) {
        AttributeKey key = this.findKeyById(command.keyId());
        AttributeValue value = this.findValueById(key, command.valueId());

        key.changeAbbreviation(value, command.newAbbreviation());
        this.repository.save(key);
        this.publishEvents(key);

        return this.mapper.toDto(key);
    }

    @Transactional
    public boolean delete(AttributeKeyCommand.@NonNull DeleteKey command) {
        AttributeKey key = this.findKeyById(command.id());

        this.repository.delete(key);
        this.publishEvents(key);

        return true;
    }

    private @NonNull AttributeKey findKeyById(@NonNull AttributeKeyId id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        AttributeRule.AttributeKey.Generic.NOT_FOUND_MSG, id));
    }

    private @NonNull AttributeValue findValueById(@NonNull AttributeKey key, @NonNull AttributeValueId valueId) {
        return key.getValues().stream()
                .filter(v -> v.getId().equals(valueId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        AttributeRule.AttributeValue.Generic.NOT_FOUND_MSG, key.getId(), valueId));
    }

    private void publishEvents(@NonNull AttributeKey key) {
        key.domainEvents().forEach(this.publisher::publishEvent);
        key.clearEvents();
    }
}
