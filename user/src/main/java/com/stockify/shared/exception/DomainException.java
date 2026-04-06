package com.stockify.shared.exception;

import lombok.Getter;

@Getter
public abstract class DomainException extends RuntimeException {

    private final String messageCode;
    private final Object[] args;

    protected DomainException(String messageCode, Object... args) {
        super(messageCode);
        this.messageCode = messageCode;
        this.args = args;
    }
}

