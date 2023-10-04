package pl.dgadecki.springworkshoprestapi.business.customer.dto.api;

import pl.dgadecki.springworkshoprestapi.business.customer.dto.Customer;

public record UpdateCustomerRequest(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber
) {

    public Customer toCustomer() {
        return new Customer(id, firstName, lastName, email, phoneNumber);
    }
}
