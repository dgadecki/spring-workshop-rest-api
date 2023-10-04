package pl.dgadecki.springworkshoprestapi.business.customer.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dgadecki.springworkshoprestapi.business.customer.domain.repository.CustomerRepository;
import pl.dgadecki.springworkshoprestapi.business.customer.dto.Customer;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<Customer> findAllCustomers() {
        return customerRepository.find();
    }

    public Customer fetchCustomerById(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new IllegalArgumentException("Customer with id " + customerId + " does not exist"));
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long customerId, Customer customer) {
        if (!customerId.equals(customer.id())) {
            throw new IllegalArgumentException("Customer id from path variable " + customerId + " does not match customer id from Customer to update " + customer.id());
        }
        return customerRepository.update(customer);
    }

    public void deleteCustomer(Long customerId) {
        customerRepository.delete(customerId);
    }
}
