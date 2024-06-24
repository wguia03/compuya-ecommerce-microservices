package com.compuya.ecommerce.user;

public record UserUpdateRequest(
        Integer id,
        String username,
        String password
) {
}
