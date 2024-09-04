package xedlab.org.applicationService.mapper;

import org.springframework.stereotype.Component;
import org.xedlab.commonDomain.valueobject.CustomerId;
import org.xedlab.commonDomain.valueobject.Money;
import org.xedlab.commonDomain.valueobject.ProductId;
import org.xedlab.commonDomain.valueobject.RestaurantId;
import org.xedlab.orderService.coreDomain.entity.Order;
import org.xedlab.orderService.coreDomain.entity.OrderItem;
import org.xedlab.orderService.coreDomain.entity.Product;
import org.xedlab.orderService.coreDomain.entity.Restaurant;
import org.xedlab.orderService.coreDomain.valueobject.StreetAddress;
import xedlab.org.applicationService.dto.create.CreateOrderCommand;
import xedlab.org.applicationService.dto.create.CreateOrderResponse;
import xedlab.org.applicationService.dto.create.OrderAddress;
import xedlab.org.applicationService.dto.track.TrackOrderResponse;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {

    public Restaurant createOrderCommandToRestaurant(CreateOrderCommand createOrderCommand) {
        return Restaurant.Builder.builder()
                .id(new RestaurantId(createOrderCommand.restaurantId()))
                .products(createOrderCommand.items().stream().map(
                                orderItem -> new Product(new ProductId(orderItem.productId())))
                        .collect(Collectors.toList()))

                .build();
    }

    public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand) {
        return Order.Builder.builder()
                .customerId(new CustomerId(createOrderCommand.customerId()))
                .restaurantId(new RestaurantId(createOrderCommand.restaurantId()))
                .deliveryAddress(orderAddressToStreetAddress(createOrderCommand.orderAddress()))
                .price(new Money(createOrderCommand.price()))
                .items(orderItemsToOrderItemsEntities(createOrderCommand.items()))
                .build();
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order, String message) {
        return new CreateOrderResponse(order.getTrackingId().getValue(), order.getStatus(), message);
    }

    public TrackOrderResponse orderToTrackOrderResponse(Order order) {
        return new TrackOrderResponse(order.getTrackingId().getValue(), order.getStatus(), order.getFailureMessages());
    }

    private StreetAddress orderAddressToStreetAddress(OrderAddress orderAddress) {
        return new StreetAddress(
                UUID.randomUUID(),
                orderAddress.street(),
                orderAddress.postalCode(),
                orderAddress.city()
        );
    }

    private List<OrderItem> orderItemsToOrderItemsEntities(
            List<xedlab.org.applicationService.dto.create.OrderItem> orderItems
    ) {
        return orderItems.stream()
                .map(orderItem ->
                        OrderItem.Builder.builder()
                                .product(new Product(new ProductId(orderItem.productId())))
                                .price(new Money(orderItem.price()))
                                .quantity(orderItem.quantity())
                                .subTotal(new Money(orderItem.subTotal()))
                                .build()).collect(Collectors.toList());
    }

}
