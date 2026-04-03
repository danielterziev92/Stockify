package com.stockify.catalog.product.domain.event;

import com.stockify.catalog.product.domain.vo.AttributeKeyId;
import com.stockify.catalog.product.domain.vo.AttributeValueId;
import org.jmolecules.event.types.DomainEvent;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.time.Instant;

public sealed interface AttributeEvent extends DomainEvent permits
        AttributeEvent.KeyCreated,
        AttributeEvent.KeyRenamed,
        AttributeEvent.ValueAdded,
        AttributeEvent.ValueReplaced,
        AttributeEvent.ValueRemoved,
        AttributeEvent.ValueChanged,
        AttributeEvent.AbbreviationChanged {

    record KeyCreated(
            @NonNull AttributeKeyId keyId,
            @NonNull String name,
            @NonNull Instant occurredAt
    ) implements AttributeEvent {
        public KeyCreated(@NonNull AttributeKeyId keyId, @NonNull String name) {
            this(keyId, name, Instant.now());
        }
    }

    record KeyRenamed(
            @NonNull AttributeKeyId keyId,
            @NonNull String oldName,
            @NonNull String newName,
            @NonNull Instant occurredAt
    ) implements AttributeEvent {
        public KeyRenamed(@NonNull AttributeKeyId keyId,
                          @NonNull String oldName,
                          @NonNull String newName) {
            this(keyId, oldName, newName, Instant.now());
        }
    }

    record ValueAdded(
            @NonNull AttributeKeyId keyId,
            @NonNull AttributeValueId valueId,
            @NonNull String value,
            @Nullable String abbreviation,
            @NonNull Instant occurredAt
    ) implements AttributeEvent {
        public ValueAdded(@NonNull AttributeKeyId keyId,
                          @NonNull AttributeValueId valueId,
                          @NonNull String value,
                          @Nullable String abbreviation) {
            this(keyId, valueId, value, abbreviation, Instant.now());
        }
    }

    record ValueReplaced(
            @NonNull AttributeKeyId keyId,
            @NonNull AttributeValueId oldValueId,
            @NonNull AttributeValueId newValueId,
            @NonNull Instant occurredAt
    ) implements AttributeEvent {
        public ValueReplaced(@NonNull AttributeKeyId keyId,
                             @NonNull AttributeValueId oldValueId,
                             @NonNull AttributeValueId newValueId) {
            this(keyId, oldValueId, newValueId, Instant.now());
        }
    }

    record ValueRemoved(
            @NonNull AttributeKeyId keyId,
            @NonNull AttributeValueId valueId,
            @NonNull Instant occurredAt
    ) implements AttributeEvent {
        public ValueRemoved(@NonNull AttributeKeyId keyId,
                            @NonNull AttributeValueId valueId) {
            this(keyId, valueId, Instant.now());
        }
    }

    record ValueChanged(
            @NonNull AttributeValueId valueId,
            @NonNull String oldValue,
            @NonNull String newValue,
            @NonNull Instant occurredAt
    ) implements AttributeEvent {
        public ValueChanged(@NonNull AttributeValueId valueId,
                            @NonNull String oldValue,
                            @NonNull String newValue) {
            this(valueId, oldValue, newValue, Instant.now());
        }
    }

    record AbbreviationChanged(
            @NonNull AttributeValueId valueId,
            @Nullable String oldAbbreviation,
            @Nullable String newAbbreviation,
            @NonNull Instant occurredAt
    ) implements AttributeEvent {
        public AbbreviationChanged(@NonNull AttributeValueId valueId,
                                   @Nullable String oldAbbreviation,
                                   @Nullable String newAbbreviation) {
            this(valueId, oldAbbreviation, newAbbreviation, Instant.now());
        }
    }
}

