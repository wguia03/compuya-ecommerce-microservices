package com.compuya.user.user;

public record UserResponse(
    Integer id,
    String firstname,
    String lastname,
    String email
) {

}
