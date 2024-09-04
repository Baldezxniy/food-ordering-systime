package org.xedlab.applicationService;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.xedlab.orderService.coreDomain.OrderDomainService;
import org.xedlab.orderService.coreDomain.OrderDomainServiceImpl;
import xedlab.org.applicationService.ports.out.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import xedlab.org.applicationService.ports.out.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import xedlab.org.applicationService.ports.out.message.publisher.restaurantapproval.OrderPaidRestaurantRequestMessagePublisher;
import xedlab.org.applicationService.ports.out.repository.CustomerRepository;
import xedlab.org.applicationService.ports.out.repository.OrderRepository;
import xedlab.org.applicationService.ports.out.repository.RestaurantRepository;

import static org.mockito.Mockito.mock;

@SpringBootApplication(scanBasePackages = "xedlab.org")
public class OrderTestConfiguration {

    @Bean
    public OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher() {
        return mock(OrderCreatedPaymentRequestMessagePublisher.class);
    }

    @Bean
    public OrderCancelledPaymentRequestMessagePublisher orderCancelledPaymentRequestMessagePublisher() {
        return mock(OrderCancelledPaymentRequestMessagePublisher.class);
    }

    @Bean
    public OrderPaidRestaurantRequestMessagePublisher orderPaidRestaurantRequestMessagePublisher() {
        return mock(OrderPaidRestaurantRequestMessagePublisher.class);
    }

    @Bean
    public RestaurantRepository restaurantRepository() {
        return mock(RestaurantRepository.class);
    }

    @Bean
    public OrderRepository orderRepository() {
        return mock(OrderRepository.class);
    }

    @Bean
    public CustomerRepository customerRepository() {
        return mock(CustomerRepository.class);
    }

    @Bean
    public OrderDomainService orderDomainService() {
        return new OrderDomainServiceImpl();
    }
}
