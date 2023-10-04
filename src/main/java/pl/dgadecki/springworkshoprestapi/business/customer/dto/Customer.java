package pl.dgadecki.springworkshoprestapi.business.customer.dto;

public record Customer(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber
) { }
