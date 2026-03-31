package com.stockify.catalog.shared.event;

import lombok.RequiredArgsConstructor;
import org.jmolecules.event.types.DomainEvent;
import org.jspecify.annotations.NonNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class SpringDomainEventPublisher implements DomainEventPublisher {

    private final ApplicationEventPublisher publisher;

    @Override
    public void publish(@NonNull Collection<? extends DomainEvent> events) {
        events.forEach(this.publisher::publishEvent);
    }
}
