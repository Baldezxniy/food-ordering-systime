package xedlab.org.applicationService.ports.out.repository;

import org.xedlab.orderService.coreDomain.entity.Order;
import org.xedlab.orderService.coreDomain.valueobject.TrackingId;

import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);

    Optional<Order> findByTrackingId(TrackingId trackingId);
    
}
