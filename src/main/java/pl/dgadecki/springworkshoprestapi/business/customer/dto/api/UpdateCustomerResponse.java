package pl.dgadecki.springworkshoprestapi.business.customer.dto.api;

import pl.dgadecki.springworkshoprestapi.business.customer.dto.Customer;

public record UpdateCustomerResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber
) {

    public static UpdateCustomerResponse from(Customer customer) {
        return new UpdateCustomerResponse(
                customer.id(),
                customer.firstName(),
                customer.lastName(),
                customer.email(),
                customer.phoneNumber()
        );
    }
}
