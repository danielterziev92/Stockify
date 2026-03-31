package com.stockify.catalog.application.product.dto;

import com.stockify.catalog.domain.product.rule.AttributeRule;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AttributeKeyRequest {

    public record CreateKey(
            @NotBlank
            @Size(max = AttributeRule.AttributeKey.Name.MAX_LENGTH)
            String name
    ) {
    }

    public record RenameKey(
            @NotBlank
            @Size(max = AttributeRule.AttributeKey.Name.MAX_LENGTH)
            String newName
    ) {
    }

    public record AddValue(
            @NotBlank
            @Size(max = AttributeRule.AttributeValue.Value.MAX_LENGTH)
            String value,

            @Nullable
            @Size(max = AttributeRule.AttributeValue.Abbreviation.MAX_LENGTH)
            String abbreviation
    ) {
    }

    public record ReplaceValue(
            @NotBlank
            @Size(max = AttributeRule.AttributeValue.Value.MAX_LENGTH)
            String newValue,

            @Nullable
            @Size(max = AttributeRule.AttributeValue.Abbreviation.MAX_LENGTH)
            String newAbbreviation
    ) {
    }
}
