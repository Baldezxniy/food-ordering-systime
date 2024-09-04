package org.xedlab.orderService.coreDomain;

import org.xedlab.orderService.coreDomain.entity.Order;
import org.xedlab.orderService.coreDomain.entity.Restaurant;
import org.xedlab.orderService.coreDomain.event.OrderCancelledEvent;
import org.xedlab.orderService.coreDomain.event.OrderCreatedEvent;
import org.xedlab.orderService.coreDomain.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {

    OrderCreatedEvent validationAndInitializeOrder(Order order, Restaurant restaurant);

    OrderPaidEvent payOrder(Order order);

    void approveOrder(Order order);

    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);

    void cancelOrder(Order order, List<String> failureMessages);
}
