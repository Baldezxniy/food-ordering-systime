package xedlab.org.applicationService.ports.out.message.publisher.restaurantapproval;

import org.xedlab.commonDomain.event.publisher.DomainEventPublisher;
import org.xedlab.orderService.coreDomain.event.OrderPaidEvent;

public interface OrderPaidRestaurantRequestMessagePublisher extends DomainEventPublisher<OrderPaidEvent> {
}
