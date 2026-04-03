package com.stockify.catalog.product.application.command;

import com.stockify.catalog.product.domain.vo.AttributeKeyId;
import com.stockify.catalog.product.domain.vo.AttributeValueId;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public sealed interface AttributeKeyCommand permits
        AttributeKeyCommand.CreateKey,
        AttributeKeyCommand.RenameKey,
        AttributeKeyCommand.DeleteKey,
        AttributeKeyCommand.AddValue,
        AttributeKeyCommand.RemoveValue,
        AttributeKeyCommand.ReplaceValue,
        AttributeKeyCommand.ChangeValue,
        AttributeKeyCommand.ChangeAbbreviation {
    record CreateKey(@NonNull String name) implements AttributeKeyCommand {
    }

    record RenameKey(@NonNull AttributeKeyId id, @NonNull String newName) implements AttributeKeyCommand {
    }

    record DeleteKey(@NonNull AttributeKeyId id) implements AttributeKeyCommand {
    }

    record AddValue(
            @NonNull AttributeKeyId keyId,
            @NonNull String value,
            @Nullable String abbreviation) implements AttributeKeyCommand {
    }

    record RemoveValue(@NonNull AttributeKeyId keyId, @NonNull AttributeKeyId valueId) implements AttributeKeyCommand {
    }

    record ReplaceValue(
            @NonNull AttributeKeyId keyId,
            @NonNull AttributeKeyId oldValueId,
            @NonNull AttributeKeyId newValueId) implements AttributeKeyCommand {
    }

    record ChangeValue(
            @NonNull AttributeValueId valueId,
            @NonNull String newValue) implements AttributeKeyCommand {
    }

    record ChangeAbbreviation(
            @NonNull AttributeKeyId valueId,
            @Nullable String newAbbreviation) implements AttributeKeyCommand {
    }
}
