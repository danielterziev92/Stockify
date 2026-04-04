package com.stockify.catalog.product.infrastructure.graphql;

import com.stockify.catalog.product.application.command.AttributeKeyCommand;
import com.stockify.catalog.product.application.dto.AttributeKeyDto;
import com.stockify.catalog.product.application.query.AttributeKeyQuery;
import com.stockify.catalog.product.application.service.AttributeKeyCommandService;
import com.stockify.catalog.product.domain.vo.AttributeKeyId;
import com.stockify.catalog.product.domain.vo.AttributeValueId;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AttributeKeyGraphQLController {

    private final AttributeKeyCommandService commandService;
    private final AttributeKeyQueryService queryService;

    @QueryMapping
    public List<AttributeKeyDto> attributeKeys() {
        return queryService.findAll(new AttributeKeyQuery.FindAll());
    }

    @QueryMapping
    public AttributeKeyDto attributeKey(@Argument UUID id) {
        return this.queryService.findById(new AttributeKeyQuery.FindById(AttributeKeyId.of(id)));
    }

    @QueryMapping
    public List<AttributeKeyDto> searchAttributeKeys(@Argument String name) {
        return queryService.searchByName(new AttributeKeyQuery.SearchByName(name));
    }

    @MutationMapping
    public AttributeKeyDto createAttributeKey(@Argument String name) {
        return commandService.create(new AttributeKeyCommand.CreateKey(name));
    }

    @MutationMapping
    public AttributeKeyDto createAttributeKeyWithValues(
            @Argument String name,
            @Argument List<AttributeKeyCommand.CreateKeyWithValues.AttributeValueInput> values
    ) {
        return this.commandService.createWithValues(
                new AttributeKeyCommand.CreateKeyWithValues(name, values)
        );
    }

    @MutationMapping
    public AttributeKeyDto renameAttributeKey(@Argument UUID id, @Argument String newName) {
        return commandService.rename(new AttributeKeyCommand.RenameKey(
                AttributeKeyId.of(id), newName));
    }

    @MutationMapping
    public boolean deleteAttributeKey(@Argument UUID id) {
        return commandService.delete(new AttributeKeyCommand.DeleteKey(AttributeKeyId.of(id)));
    }

    @MutationMapping
    public AttributeKeyDto addAttributeValue(
            @Argument UUID keyId,
            @Argument String value,
            @Argument String abbreviation) {
        return commandService.addValue(new AttributeKeyCommand.AddValue(
                AttributeKeyId.of(keyId), value, abbreviation));
    }

    @MutationMapping
    public AttributeKeyDto removeAttributeValue(@Argument UUID keyId, @Argument UUID valueId) {
        return commandService.removeValue(new AttributeKeyCommand.RemoveValue(
                AttributeKeyId.of(keyId), AttributeValueId.of(valueId)));
    }

    @MutationMapping
    public AttributeKeyDto replaceAttributeValue(
            @Argument UUID keyId,
            @Argument UUID oldValueId,
            @Argument UUID newValueId) {
        return commandService.replaceValue(new AttributeKeyCommand.ReplaceValue(
                AttributeKeyId.of(keyId),
                AttributeValueId.of(oldValueId),
                AttributeValueId.of(newValueId)));
    }

    @MutationMapping
    public AttributeKeyDto changeAttributeValue(
            @Argument UUID keyId,
            @Argument UUID valueId,
            @Argument String newValue) {
        return commandService.changeValue(new AttributeKeyCommand.ChangeValue(
                AttributeKeyId.of(keyId), AttributeValueId.of(valueId), newValue));
    }

    @MutationMapping
    public AttributeKeyDto changeAttributeAbbreviation(
            @Argument UUID keyId,
            @Argument UUID valueId,
            @Argument String newAbbreviation) {
        return commandService.changeAbbreviation(new AttributeKeyCommand.ChangeAbbreviation(
                AttributeKeyId.of(keyId), AttributeValueId.of(valueId), newAbbreviation));
    }
}
