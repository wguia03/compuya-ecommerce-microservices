package com.compuya.ecommerce.user;

public record UserResponse(
    Integer id,
    String firstname,
    String lastname,
    String email
) {

}
