package xedlab.org.applicationService.ports.out.repository;

import org.xedlab.orderService.coreDomain.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepository {

    Optional<Restaurant> findRestaurantInformation(Restaurant restaurant);

}
