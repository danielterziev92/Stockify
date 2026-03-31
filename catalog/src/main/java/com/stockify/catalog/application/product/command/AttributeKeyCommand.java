package com.stockify.catalog.application.product.command;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

public sealed interface AttributeKeyCommand permits
        AttributeKeyCommand.CreateKey,
        AttributeKeyCommand.RenameKey,
        AttributeKeyCommand.AddValue,
        AttributeKeyCommand.RemoveValue,
        AttributeKeyCommand.ReplaceValue {

    record CreateKey(@NonNull String name) implements AttributeKeyCommand {
    }

    record RenameKey(
            @NonNull UUID keyId,
            @NonNull String newName
    ) implements AttributeKeyCommand {
    }

    record AddValue(
            @NonNull UUID keyId,
            @NonNull String value,
            @Nullable String abbreviation
    ) implements AttributeKeyCommand {
    }

    record RemoveValue(
            @NonNull UUID keyId,
            @NonNull UUID valueId
    ) implements AttributeKeyCommand {
    }

    record ReplaceValue(
            @NonNull UUID keyId,
            @NonNull UUID oldValueId,
            @NonNull String newValue,
            @Nullable String newAbbreviation
    ) implements AttributeKeyCommand {
    }
}
