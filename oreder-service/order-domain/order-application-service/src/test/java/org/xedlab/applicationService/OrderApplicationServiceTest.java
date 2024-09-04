package org.xedlab.applicationService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.xedlab.commonDomain.valueobject.*;
import org.xedlab.orderService.coreDomain.entity.Customer;
import org.xedlab.orderService.coreDomain.entity.Order;
import org.xedlab.orderService.coreDomain.entity.Product;
import org.xedlab.orderService.coreDomain.entity.Restaurant;
import org.xedlab.orderService.coreDomain.exception.OrderDomainException;
import xedlab.org.applicationService.dto.create.CreateOrderCommand;
import xedlab.org.applicationService.dto.create.CreateOrderResponse;
import xedlab.org.applicationService.dto.create.OrderAddress;
import xedlab.org.applicationService.dto.create.OrderItem;
import xedlab.org.applicationService.mapper.OrderDataMapper;
import xedlab.org.applicationService.ports.in.service.OrderApplicationService;
import xedlab.org.applicationService.ports.out.repository.CustomerRepository;
import xedlab.org.applicationService.ports.out.repository.OrderRepository;
import xedlab.org.applicationService.ports.out.repository.RestaurantRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = {OrderTestConfiguration.class})
public class OrderApplicationServiceTest {

    @Autowired
    private OrderApplicationService orderApplicationService;

    @Autowired
    private OrderDataMapper orderDataMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    private CreateOrderCommand createOrderCommand;
    private CreateOrderCommand createOrderCommandWrongPrice;
    private CreateOrderCommand createOrderCommandWrongProductPrice;

    private final UUID CUSTOMER_ID = UUID.fromString("53149356-8d70-4a35-860c-dad8456ec5b3");
    private final UUID RESTAURANT_ID = UUID.fromString("a935539d-756a-405d-a550-2de55f2a2081");
    private final UUID PRODUCT_ID = UUID.fromString("5e5ab24a-c810-4d16-abe1-d6ffa89a4923");
    private final UUID ORDER_ID = UUID.fromString("131742ce-c805-4474-ac1a-e101154b0bb0");
    private final BigDecimal PRICE = new BigDecimal("200.00");

    @BeforeAll
    public void init() {
        createOrderCommand = new CreateOrderCommand(
                CUSTOMER_ID, RESTAURANT_ID, PRICE,
                List.of(
                        new OrderItem(PRODUCT_ID, 1,
                                new BigDecimal("50.00"),
                                new BigDecimal("50.00")),
                        new OrderItem(
                                PRODUCT_ID, 3,
                                new BigDecimal("50.00"),
                                new BigDecimal("150.00")
                        )
                ),
                new OrderAddress("street_1", "23123A", "Moscow")
        );

        createOrderCommandWrongPrice = new CreateOrderCommand(
                CUSTOMER_ID, RESTAURANT_ID, new BigDecimal("250.00"),
                List.of(
                        new OrderItem(PRODUCT_ID, 1,
                                new BigDecimal("50.00"),
                                new BigDecimal("50.00")),
                        new OrderItem(
                                PRODUCT_ID, 3,
                                new BigDecimal("50.00"),
                                new BigDecimal("150.00")
                        )
                ),
                new OrderAddress("street_1", "23123A", "Moscow")
        );

        createOrderCommandWrongProductPrice = new CreateOrderCommand(
                CUSTOMER_ID, RESTAURANT_ID, new BigDecimal("210.00"),
                List.of(
                        new OrderItem(PRODUCT_ID, 1,
                                new BigDecimal("60.00"),
                                new BigDecimal("60.00")),
                        new OrderItem(
                                PRODUCT_ID, 3,
                                new BigDecimal("50.00"),
                                new BigDecimal("150.00")
                        )
                ),
                new OrderAddress("street_1", "23123A", "Moscow")
        );

        Customer customer = new Customer();
        customer.setId(new CustomerId(CUSTOMER_ID));

        Restaurant restaurantResponse = Restaurant.Builder.builder()
                .id(new RestaurantId(createOrderCommand.restaurantId()))
                .products(List.of(
                        new Product(
                                new ProductId(PRODUCT_ID),
                                "product-1",
                                new Money(new BigDecimal("50.00"))
                        ),
                        new Product(
                                new ProductId(PRODUCT_ID),
                                "product-2",
                                new Money(new BigDecimal("50.00"))
                        )
                ))
                .active(true)
                .build();

        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        order.setId(new OrderId(ORDER_ID));

        when(customerRepository.findCustomer(CUSTOMER_ID))
                .thenReturn(Optional.of(customer));
        when(restaurantRepository.findRestaurantInformation(orderDataMapper.createOrderCommandToRestaurant(createOrderCommand)))
                .thenReturn(Optional.of(restaurantResponse));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
    }

    @Test
    void testCreateOrder() {
        CreateOrderResponse createOrderResponse = orderApplicationService.createOrder(createOrderCommand);

        assertEquals(OrderStatus.PENDING, createOrderResponse.orderStatus());
        assertEquals("Order Created Successfully", createOrderResponse.message());
        assertNotNull(createOrderResponse.orderTrackingId());
    }

    @Test
    void testCreateOrderWithWrongTotalPrice() {
        OrderDomainException orderDomainException = assertThrows(OrderDomainException.class,
                () -> orderApplicationService.createOrder(createOrderCommandWrongPrice));
        assertEquals("Total price: 250.00 is not equal to 200.00", orderDomainException.getMessage());
    }

    @Test
    void testCreateOrderWithWrongProductPrice() {
        OrderDomainException orderDomainException = assertThrows(OrderDomainException.class,
                () -> orderApplicationService.createOrder(createOrderCommandWrongProductPrice));
        assertEquals("Order item price: 60.00 is not valid for product: " + PRODUCT_ID, orderDomainException.getMessage());
    }

    @Test
    void testCreateOrderWithPassiveRestaurant() {
        Restaurant restaurantResponse = Restaurant.Builder.builder()
                .id(new RestaurantId(createOrderCommand.restaurantId()))
                .products(List.of(
                        new Product(
                                new ProductId(PRODUCT_ID),
                                "product-1",
                                new Money(new BigDecimal("50.00"))
                        ),
                        new Product(
                                new ProductId(PRODUCT_ID),
                                "product-2",
                                new Money(new BigDecimal("50.00"))
                        )
                ))
                .active(false)
                .build();

        when(restaurantRepository.findRestaurantInformation(orderDataMapper.createOrderCommandToRestaurant(createOrderCommand)))
                .thenReturn(Optional.of(restaurantResponse));

        OrderDomainException orderDomainException = assertThrows(OrderDomainException.class,
                () -> orderApplicationService.createOrder(createOrderCommand));

        assertEquals("Restaurant with id " + RESTAURANT_ID + " is currently not active!", orderDomainException.getMessage());

    }
}
