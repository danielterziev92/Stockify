package com.stockify.catalog.event;

import org.springframework.context.ApplicationEvent;

public class ProductCategoryRefreshEvent extends ApplicationEvent {
    public ProductCategoryRefreshEvent(Object source) {
        super(source);
    }
}
