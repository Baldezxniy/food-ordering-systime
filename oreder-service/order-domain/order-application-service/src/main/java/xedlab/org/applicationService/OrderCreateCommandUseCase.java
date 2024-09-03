package xedlab.org.applicationService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xedlab.orderService.coreDomain.OrderDomainService;
import org.xedlab.orderService.coreDomain.entity.Customer;
import org.xedlab.orderService.coreDomain.entity.Order;
import org.xedlab.orderService.coreDomain.entity.Restaurant;
import org.xedlab.orderService.coreDomain.event.OrderCreatedEvent;
import org.xedlab.orderService.coreDomain.exception.OrderDomainException;
import xedlab.org.applicationService.dto.create.CreateOrderCommand;
import xedlab.org.applicationService.dto.create.CreateOrderResponse;
import xedlab.org.applicationService.mapper.OrderDataMapper;
import xedlab.org.applicationService.ports.out.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import xedlab.org.applicationService.ports.out.repository.CustomerRepository;
import xedlab.org.applicationService.ports.out.repository.OrderRepository;
import xedlab.org.applicationService.ports.out.repository.RestaurantRepository;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
class OrderCreateCommandUseCase {

    private final OrderCreateUseCase orderCreateUseCase;

    private final OrderDataMapper orderDataMapper;

    private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;

    OrderCreateCommandUseCase(
            OrderCreateUseCase orderCreateUseCase,
            OrderDataMapper orderDataMapper,
            OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher
    ) {
        this.orderCreateUseCase = orderCreateUseCase;
        this.orderDataMapper = orderDataMapper;
        this.orderCreatedPaymentRequestMessagePublisher = orderCreatedPaymentRequestMessagePublisher;
    }

    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        OrderCreatedEvent orderCreatedEvent = orderCreateUseCase.persistOrder(createOrderCommand);
        orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);
        return orderDataMapper.orderToCreateOrderResponse(orderCreatedEvent.getOrder());

    }

}
