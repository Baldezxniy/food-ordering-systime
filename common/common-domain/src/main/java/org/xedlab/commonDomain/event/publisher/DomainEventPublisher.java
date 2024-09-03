package org.xedlab.commonDomain.event.publisher;

import org.xedlab.commonDomain.event.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent> {

    void publish(T domainEvent);

}
