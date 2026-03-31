package com.stockify.catalog.shared.exception;

public class BusinessRuleException extends DomainException {

    public BusinessRuleException(String messageCode, Object... args) {
        super(messageCode, args);
    }
}
