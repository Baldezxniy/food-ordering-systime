package org.xedlab.orderService.coreDomain.event;

import org.xedlab.commonDomain.event.DomainEvent;
import org.xedlab.orderService.coreDomain.entity.Order;

import java.time.ZonedDateTime;

public class OrderCreatedEvent implements DomainEvent<Order> {
    private final Order order;
    private final ZonedDateTime createdAt;

    public OrderCreatedEvent(Order order, ZonedDateTime createdAt) {
        this.order = order;
        this.createdAt = createdAt;
    }

    public Order getOrder() {
        return order;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
