package pl.dgadecki.springworkshoprestapi.business.customer.domain.repository;

import org.springframework.stereotype.Repository;
import pl.dgadecki.springworkshoprestapi.business.customer.dto.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CustomerRepository {

    private final Map<Long, Customer> customers = new ConcurrentHashMap<>();

    public Customer save(Customer customer) {
        Long customerId = customer.id() != null ? customer.id() : new Random().nextLong();
        Customer customerToSave = new Customer(customerId, customer.firstName(), customer.lastName(), customer.email(), customer.phoneNumber());
        customers.put(customerId, customerToSave);
        return customers.get(customerId);
    }

    public Customer update(Customer customer) {
        if (customers.containsKey(customer.id())) {
            customers.put(customer.id(), customer);
            return customers.get(customer.id());
        } else {
            throw new IllegalArgumentException("Customer with id " + customer.id() + " does not exist");
        }
    }

    public void delete(Long customerId) {
        if (customers.containsKey(customerId)) {
            customers.remove(customerId);
        } else {
            throw new IllegalArgumentException("Customer with id " + customerId + " does not exist");
        }
    }

    public Optional<Customer> findById(Long customerId) {
        return Optional.ofNullable(customers.get(customerId));
    }

    public List<Customer> find() {
        return new ArrayList<>(customers.values());
    }
}
