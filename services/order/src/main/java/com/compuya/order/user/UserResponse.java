package com.compuya.order.user;

public record UserResponse(
    Integer id,
    String firstname,
    String lastname,
    String email
) {

}
