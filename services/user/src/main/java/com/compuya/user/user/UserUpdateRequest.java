package com.compuya.user.user;

public record UserUpdateRequest(
        Integer id,
        String username,
        String password
) {
}
