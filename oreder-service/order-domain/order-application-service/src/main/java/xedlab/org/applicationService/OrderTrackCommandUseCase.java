package xedlab.org.applicationService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xedlab.orderService.coreDomain.entity.Order;
import org.xedlab.orderService.coreDomain.exception.OrderNotFoundException;
import org.xedlab.orderService.coreDomain.valueobject.TrackingId;
import xedlab.org.applicationService.dto.track.TrackOrderQuery;
import xedlab.org.applicationService.dto.track.TrackOrderResponse;
import xedlab.org.applicationService.mapper.OrderDataMapper;
import xedlab.org.applicationService.ports.out.repository.OrderRepository;

import java.util.Optional;

@Slf4j
@Service
class OrderTrackCommandUseCase {

    private final OrderRepository orderRepository;
    private final OrderDataMapper orderDataMapper;

    OrderTrackCommandUseCase(
            OrderRepository orderRepository,
            OrderDataMapper orderDataMapper
    ) {
        this.orderRepository = orderRepository;
        this.orderDataMapper = orderDataMapper;
    }

    @Transactional(readOnly = true)
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        Optional<Order> orderOption =
                orderRepository.findByTrackingId(new TrackingId(trackOrderQuery.orderTrackingId()));
        if (orderOption.isEmpty()) {
            log.warn("Could not find order with tracing id: {}", trackOrderQuery.orderTrackingId());
            throw new OrderNotFoundException("Could not find order with tracing id: " +
                    trackOrderQuery.orderTrackingId());
        }
        return orderDataMapper.orderToTrackOrderResponse(orderOption.get());
    }

}
