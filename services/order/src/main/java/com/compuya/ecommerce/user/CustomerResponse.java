package com.compuya.ecommerce.user;

public record CustomerResponse(
    Integer id,
    String firstname,
    String lastname,
    String email
) {

}
