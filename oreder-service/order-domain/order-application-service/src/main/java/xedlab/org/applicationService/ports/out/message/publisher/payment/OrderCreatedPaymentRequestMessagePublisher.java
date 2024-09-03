package xedlab.org.applicationService.ports.out.message.publisher.payment;

import org.xedlab.commonDomain.event.publisher.DomainEventPublisher;
import org.xedlab.orderService.coreDomain.event.OrderCreatedEvent;

public interface OrderCreatedPaymentRequest extends DomainEventPublisher<OrderCreatedEvent> {
}
