package com.stockify.catalog.shared.event;

import org.jmolecules.event.types.DomainEvent;
import org.jspecify.annotations.NonNull;

import java.util.Collection;

public interface DomainEventPublisher {

    void publish(@NonNull Collection<? extends DomainEvent> events);
}
