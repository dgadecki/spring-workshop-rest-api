package pl.dgadecki.springworkshoprestapi.business.customer.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.dgadecki.springworkshoprestapi.business.customer.domain.service.CustomerService;
import pl.dgadecki.springworkshoprestapi.business.customer.dto.Customer;
import pl.dgadecki.springworkshoprestapi.business.customer.dto.api.CreateCustomerRequest;
import pl.dgadecki.springworkshoprestapi.business.customer.dto.api.CreateCustomerResponse;
import pl.dgadecki.springworkshoprestapi.business.customer.dto.api.CustomerResponse;
import pl.dgadecki.springworkshoprestapi.business.customer.dto.api.FindAllCustomersResponse;
import pl.dgadecki.springworkshoprestapi.business.customer.dto.api.UpdateCustomerRequest;
import pl.dgadecki.springworkshoprestapi.business.customer.dto.api.UpdateCustomerResponse;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FindAllCustomersResponse findAllCustomers() {
        return FindAllCustomersResponse.from(customerService.findAllCustomers());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerResponse fetchCustomerById(@PathVariable("id") Long customerId) {
        return CustomerResponse.from(customerService.fetchCustomerById(customerId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CreateCustomerResponse createCustomer(@RequestBody CreateCustomerRequest createCustomerRequest) {
        Customer createdCustomer = customerService.saveCustomer(createCustomerRequest.toCustomer());
        return CreateCustomerResponse.from(createdCustomer);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UpdateCustomerResponse updateCustomer(@PathVariable("id") Long customerId, @RequestBody UpdateCustomerRequest updateCustomerRequest) {
        Customer updatedCustomer = customerService.updateCustomer(customerId, updateCustomerRequest.toCustomer());
        return UpdateCustomerResponse.from(updatedCustomer);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void deleteCustomer(@PathVariable("id") Long customerId) {
        customerService.deleteCustomer(customerId);
    }
}
