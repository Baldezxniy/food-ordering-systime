package xedlab.org.applicationService.ports.out.repository;

import org.xedlab.orderService.coreDomain.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Optional<Customer> findCustomer(UUID customerId);

}
