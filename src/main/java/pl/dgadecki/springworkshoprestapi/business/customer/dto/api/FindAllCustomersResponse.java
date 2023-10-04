package pl.dgadecki.springworkshoprestapi.business.customer.dto.api;

import pl.dgadecki.springworkshoprestapi.business.customer.dto.Customer;

import java.util.List;

public record FindAllCustomersResponse(
        List<CustomerResponse> customers
) {

    public static FindAllCustomersResponse from(List<Customer> customers) {
        List<CustomerResponse> customerResponses = customers.stream()
                .map(CustomerResponse::from)
                .toList();

        return new FindAllCustomersResponse(customerResponses);
    }
}
