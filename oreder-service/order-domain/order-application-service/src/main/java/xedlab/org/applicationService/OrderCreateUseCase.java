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
import xedlab.org.applicationService.mapper.OrderDataMapper;
import xedlab.org.applicationService.ports.out.repository.CustomerRepository;
import xedlab.org.applicationService.ports.out.repository.OrderRepository;
import xedlab.org.applicationService.ports.out.repository.RestaurantRepository;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
class OrderCreateUseCase {

    private final OrderDomainService orderDomainService;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderDataMapper orderDataMapper;

    OrderCreateUseCase(
            OrderDomainService orderDomainService,
            OrderRepository orderRepository,
            CustomerRepository customerRepository,
            RestaurantRepository restaurantRepository,
            OrderDataMapper orderDataMapper
    ) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderDataMapper = orderDataMapper;
    }

    @Transactional
    public OrderCreatedEvent persistOrder(CreateOrderCommand createOrderCommand) {
        checkCustomer(createOrderCommand.customerId());
        Restaurant restaurant = checkRestaurant(createOrderCommand);
        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validationAndInitializeOrder(order, restaurant);
        saveOrder(order);
        log.info("Order is created with id: {}", orderCreatedEvent.getOrder().getId().getValue());
        return orderCreatedEvent;
    }


    private void checkCustomer(UUID customerId) {
        Optional<Customer> customer = customerRepository.findCustomer(customerId);

        if (customer.isEmpty()) {
            log.warn("Could not find customer with customer id: {}", customerId);
            throw new OrderDomainException("Could not find customer with customer id: " + customerId);
        }
    }

    private Restaurant checkRestaurant(CreateOrderCommand createOrderCommand) {
        Restaurant restaurant = orderDataMapper.createOrderCommandToRestaurant(createOrderCommand);
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findRestaurantInformation(restaurant);
        if (optionalRestaurant.isEmpty()) {
            log.warn("Could not find restaurant with restaurant id: {}", restaurant.getId().getValue());
            throw new OrderDomainException("Could not find restaurant with restaurant id: " + restaurant.getId().getValue());

        }
        return optionalRestaurant.get();
    }

    private Order saveOrder(Order order) {
        Order savedOrder = orderRepository.save(order);

        if (savedOrder == null) {
            log.error("Could not save order!");
            throw new OrderDomainException("Could not save order!");
        }

        log.info("Order is saved with id: {}", savedOrder.getId());
        return savedOrder;
    }
}
