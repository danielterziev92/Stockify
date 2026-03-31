package com.stockify.catalog.infrastructure.event;


import com.stockify.catalog.shared.event.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.jmolecules.event.types.DomainEvent;
import org.jspecify.annotations.NonNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class SpringApplicationDomainEventPublisher implements DomainEventPublisher {

    private final ApplicationEventPublisher springPublisher;

    @Override
    public void publish(@NonNull Collection<? extends DomainEvent> events) {
        events.forEach(springPublisher::publishEvent);
    }
}
