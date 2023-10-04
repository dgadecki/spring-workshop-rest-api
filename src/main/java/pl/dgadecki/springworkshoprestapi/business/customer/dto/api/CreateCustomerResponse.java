package pl.dgadecki.springworkshoprestapi.business.customer.dto.api;

import pl.dgadecki.springworkshoprestapi.business.customer.dto.Customer;

public record CreateCustomerResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber
) {

    public static CreateCustomerResponse from(Customer customer) {
        return new CreateCustomerResponse(
                customer.id(),
                customer.firstName(),
                customer.lastName(),
                customer.email(),
                customer.phoneNumber()
        );
    }
}
