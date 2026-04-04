package com.stockify.catalog.product.domain;

import com.stockify.catalog.exception.InvalidValueException;
import com.stockify.catalog.product.domain.event.AttributeEvent;
import com.stockify.catalog.product.domain.rule.AttributeRule;
import com.stockify.catalog.product.domain.vo.AttributeKeyId;
import com.stockify.catalog.product.domain.vo.AttributeValueId;
import jakarta.annotation.Nonnull;
import lombok.Getter;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.event.types.DomainEvent;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.*;


@Getter
public class AttributeKey implements AggregateRoot<AttributeKey, AttributeKeyId>, Persistable<AttributeKeyId> {

    @Id
    private final AttributeKeyId id;
    private String name;
    @MappedCollection(idColumn = "key_id")
    private final Set<AttributeValue> values;

    @Transient
    private final List<DomainEvent> events;

    private AttributeKey(AttributeKeyId id, String name) {
        this.id = id;
        this.name = name;
        this.values = new HashSet<>();
        this.events = new ArrayList<>();
    }

    public static @NonNull AttributeKey create(@Nonnull String name) {
        validateName(name);

        AttributeKey key = new AttributeKey(AttributeKeyId.generate(), name);
        key.events.add(new AttributeEvent.KeyCreated(key.id, name));

        return key;
    }

    public static @NonNull AttributeKey reconstitute(
            @NonNull AttributeKeyId id,
            @NonNull String name,
            @NonNull Set<AttributeValue> values
    ) {
        AttributeKey key = new AttributeKey(id, name);
        key.values.addAll(values);

        return key;
    }

    public void rename(@NonNull String newName) {
        if (this.name.equals(newName)) return;

        validateName(newName);

        this.events.add(new AttributeEvent.KeyRenamed(this.id, this.name, newName));
        this.name = newName;
    }

    public void addValue(@NonNull AttributeValue value) throws InvalidValueException {
        if (this.values.contains(value))
            throw new InvalidValueException(
                    AttributeRule.AttributeValue.Generic.ALREADY_EXISTS_MSG, this.id, this.name, value.getValue());

        this.values.add(value);
        this.events.add(new AttributeEvent.ValueAdded(id, value.getId(), value.getValue(), value.getAbbreviation()));
    }

    public void addValue(@NonNull String value, @Nullable String abbreviation) {
        if (this.values.stream().anyMatch(v -> v.getValue().equalsIgnoreCase(value)))
            throw this.valueAlreadyExistsException(value);

        this.addValue(AttributeValue.create(value, abbreviation));
    }

    public void replaceValue(
            @NonNull AttributeValue oldValue,
            @NonNull AttributeValue newValue
    ) {
        if (!this.values.contains(oldValue)) throw this.valueNotFoundException(oldValue.getValue());
        if (this.values.contains(newValue)) throw this.valueAlreadyExistsException(newValue.getValue());

        this.values.remove(oldValue);
        this.values.add(newValue);

        this.events.add(new AttributeEvent.ValueReplaced(this.id, oldValue.getId(), newValue.getId()));
    }

    public void removeValue(@NonNull AttributeValue value) {
        if (!this.values.contains(value)) throw this.valueNotFoundException(value.getValue());

        this.values.remove(value);
        this.events.add(new AttributeEvent.ValueRemoved(id, value.getId()));
    }

    public void changeValue(@NonNull AttributeValue value, @NonNull String newValue) {
        if (!this.values.contains(value)) throw this.valueNotFoundException(value.getValue());
        if (this.values.stream().anyMatch(v -> v.getValue().equalsIgnoreCase(newValue)))
            throw this.valueAlreadyExistsException(newValue);

        AttributeValue newAttributeValue = value.changeValue(newValue);

        this.values.remove(value);
        this.values.add(newAttributeValue);

        this.events.add(new AttributeEvent.ValueChanged(
                value.getId(),
                value.getValue(),
                newAttributeValue.getValue()
        ));
    }

    public void changeAbbreviation(@NonNull AttributeValue value, @Nullable String abbreviation) {
        if (!this.values.contains(value)) throw this.abbreviationNotFoundException(value.getValue());

        AttributeValue newAttributeValue = value.withAbbreviation(abbreviation);

        this.values.remove(value);
        this.values.add(newAttributeValue);

        this.events.add(new AttributeEvent.AbbreviationChanged(
                value.getId(),
                value.getAbbreviation(),
                newAttributeValue.getAbbreviation()
        ));
    }

    public boolean hasValue(@NonNull AttributeValueId valueId) {
        return values.stream().anyMatch(v -> v.getId().equals(valueId));
    }

    public @NonNull List<DomainEvent> domainEvents() {
        return Collections.unmodifiableList(events);
    }

    public void clearEvents() {
        this.events.clear();
    }

    @Override
    public boolean isNew() {
        return this.events.getFirst() instanceof AttributeEvent.KeyCreated;
    }

    private static void validateName(@NonNull String name) throws InvalidValueException {
        if (name.isBlank())
            throw new InvalidValueException(AttributeRule.AttributeKey.Name.BLANK_MSG);

        if (name.length() > AttributeRule.AttributeKey.Name.MAX_LENGTH)
            throw new InvalidValueException(AttributeRule.AttributeKey.Name.MAX_LENGTH_MSG, name.length());
    }

    private @NonNull InvalidValueException valueNotFoundException(@NonNull String value) {
        return new InvalidValueException(AttributeRule.AttributeValue.Generic.NOT_FOUND_MSG, this.id, AttributeValue.Fields.value, this.name, value);
    }

    private @NonNull InvalidValueException valueAlreadyExistsException(@NonNull String value) {
        return new InvalidValueException(AttributeRule.AttributeValue.Generic.ALREADY_EXISTS_MSG, this.id, AttributeValue.Fields.value, this.name, value);
    }

    private @NonNull InvalidValueException abbreviationNotFoundException(@NonNull String abbreviation) {
        return new InvalidValueException(AttributeRule.AttributeValue.Generic.NOT_FOUND_MSG, this.id, AttributeValue.Fields.abbreviation, this.name, abbreviation);
    }
}
