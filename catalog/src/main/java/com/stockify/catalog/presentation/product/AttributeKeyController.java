package com.stockify.catalog.presentation.product;

import com.stockify.catalog.application.product.command.AttributeKeyCommand;
import com.stockify.catalog.application.product.command.AttributeKeyCommandService;
import com.stockify.catalog.application.product.dto.AttributeKeyRequest;
import com.stockify.catalog.application.product.query.AttributeKeyQuery;
import com.stockify.catalog.application.product.query.AttributeKeyQueryService;
import com.stockify.catalog.application.product.query.AttributeKeyResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/attribute-keys")
@RequiredArgsConstructor
public class AttributeKeyController {

    private final AttributeKeyCommandService commandService;
    private final AttributeKeyQueryService queryService;

    @GetMapping(version = "1")
    public ResponseEntity<List<AttributeKeyResponse.KeySummary>> getAll() {
        List<AttributeKeyResponse.KeySummary> result = this.queryService.handle(new AttributeKeyQuery.GetAll());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @GetMapping(value = "/{keyId}", version = "1")
    public ResponseEntity<AttributeKeyResponse.KeySummary> getById(@PathVariable UUID keyId) {
        AttributeKeyResponse.KeySummary result = this.queryService.handle(new AttributeKeyQuery.GetById(keyId));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @PostMapping(version = "1")
    public ResponseEntity<Void> createKey(
            @Valid @RequestBody AttributeKeyRequest.CreateKey request
    ) {
        UUID createdId = this.commandService.handle(new AttributeKeyCommand.CreateKey(request.name()));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdId)
                .toUri();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(location)
                .build();
    }

    @PatchMapping(value = "/{keyId}/name", version = "1")
    public ResponseEntity<Void> renameKey(
            @PathVariable UUID keyId,
            @Valid @RequestBody AttributeKeyRequest.RenameKey request
    ) {
        this.commandService.handle(new AttributeKeyCommand.RenameKey(keyId, request.newName()));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PostMapping(value = "/{keyId}/values", version = "1")
    public ResponseEntity<Void> addValue(
            @PathVariable UUID keyId,
            @Valid @RequestBody AttributeKeyRequest.AddValue request
    ) {
        UUID valueId = this.commandService.handle(
                new AttributeKeyCommand.AddValue(keyId, request.value(), request.abbreviation())
        );

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(valueId)
                .toUri();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(location)
                .build();
    }

    @PutMapping(value = "/{keyId}/values/{valueId}", version = "1")
    public ResponseEntity<Void> replaceValue(
            @PathVariable UUID keyId,
            @PathVariable UUID valueId,
            @Valid @RequestBody AttributeKeyRequest.ReplaceValue request
    ) {
        this.commandService.handle(new AttributeKeyCommand.ReplaceValue(
                keyId, valueId, request.newValue(), request.newAbbreviation()
        ));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/{keyId}/values/{valueId}")
    public ResponseEntity<Void> removeValue(
            @PathVariable UUID keyId,
            @PathVariable UUID valueId
    ) {
        commandService.handle(new AttributeKeyCommand.RemoveValue(keyId, valueId));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
