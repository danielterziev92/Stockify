package com.stockify.catalog.domain.product;

import com.stockify.catalog.domain.product.event.AttributeEvent;
import com.stockify.catalog.domain.product.rule.AttributeRule;
import com.stockify.catalog.domain.product.vo.AttributeKeyId;
import com.stockify.catalog.domain.product.vo.AttributeKeyName;
import com.stockify.catalog.domain.product.vo.AttributeValueId;
import com.stockify.catalog.shared.exception.EntityNotFoundException;
import com.stockify.catalog.shared.exception.InvalidValueException;
import jakarta.annotation.Nonnull;
import lombok.Getter;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.event.types.DomainEvent;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Getter
public class AttributeKey implements AggregateRoot<AttributeKey, AttributeKeyId> {

    private final AttributeKeyId id;
    private AttributeKeyName name;
    private final List<AttributeValue> values;

    private final List<DomainEvent> events;

    private AttributeKey(AttributeKeyId id, AttributeKeyName name) {
        this.id = id;
        this.name = name;
        this.values = new ArrayList<>();
        this.events = new ArrayList<>();
    }

    public static @NonNull AttributeKey create(@Nonnull AttributeKeyName name) {
        AttributeKey key = new AttributeKey(AttributeKeyId.generate(), name);
        key.events.add(new AttributeEvent.KeyCreated(key.id, name.value()));

        return key;
    }

    public static @NonNull AttributeKey reconstitute(
            @NonNull AttributeKeyId id,
            @NonNull AttributeKeyName name,
            @NonNull List<AttributeValue> values
    ) {
        AttributeKey key = new AttributeKey(id, name);
        key.values.addAll(values);

        return key;
    }

    public void rename(@NonNull AttributeKeyName newName) {
        if (this.name.equals(newName)) return;

        AttributeKeyName oldName = this.name;
        this.name = newName;
        this.events.add(new AttributeEvent.KeyRenamed(this.id, oldName.value(), newName.value()));
    }

    public @NonNull AttributeValueId addValue(@NonNull String value, @Nullable String abbreviation) {
        if (this.isDuplicateValue(value))
            throw new InvalidValueException(AttributeRule.AttributeValue.Value.DUPLICATE_MSG, value);

        if (abbreviation != null && this.isDuplicateAbbreviation(abbreviation))
            throw new InvalidValueException(AttributeRule.AttributeValue.Abbreviation.DUPLICATE_MSG, abbreviation);

        AttributeValue newValue = AttributeValue.create(id, value, abbreviation);
        this.values.add(newValue);
        this.events.add(new AttributeEvent.ValueAdded(id, newValue.getId(), value, abbreviation));

        return newValue.getId();
    }

    public void removeValue(@NonNull AttributeValueId valueId) {
        AttributeValue toRemove = this.findValue(valueId);
        this.values.remove(toRemove);
        this.events.add(new AttributeEvent.ValueRemoved(id, valueId));
    }

    public @NonNull AttributeValueId replaceValue(
            @NonNull AttributeValueId oldValueId,
            @NonNull String newValue,
            @Nullable String newAbbreviation
    ) {
        AttributeValue old = this.findValue(oldValueId);

        boolean valueChanges = !old.getValue().equalsIgnoreCase(newValue);
        if (valueChanges && this.isDuplicateValue(newValue))
            throw new InvalidValueException(AttributeRule.AttributeValue.Value.DUPLICATE_MSG, newValue);

        boolean abbrChanges = !java.util.Objects.equals(old.getAbbreviation(), newAbbreviation);
        if (abbrChanges && newAbbreviation != null && isDuplicateAbbreviation(newAbbreviation))
            throw new InvalidValueException(AttributeRule.AttributeValue.Abbreviation.DUPLICATE_MSG, newAbbreviation);

        this.values.remove(old);
        AttributeValue created = AttributeValue.create(id, newValue, newAbbreviation);
        this.values.add(created);

        this.events.add(new AttributeEvent.AbbreviationChanged(
                id,
                oldValueId,
                created.getId(),
                newValue,
                old.getAbbreviation(),
                newAbbreviation
        ));

        return created.getId();
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

    private boolean isDuplicateValue(@NonNull String value) {
        return values.stream()
                .anyMatch(v -> v.getValue().equalsIgnoreCase(value));
    }

    private boolean isDuplicateAbbreviation(@NonNull String abbreviation) {
        return values.stream()
                .anyMatch(v -> abbreviation.equalsIgnoreCase(v.getAbbreviation()));
    }

    private @NonNull AttributeValue findValue(@NonNull AttributeValueId valueId) {
        return values.stream()
                .filter(v -> v.getId().equals(valueId))
                .findFirst()
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                AttributeRule.AttributeValue.Generic.NOT_FOUND_MSG,
                                valueId.value().toString()));
    }
}