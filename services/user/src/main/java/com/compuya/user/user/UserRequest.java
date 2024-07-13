package com.compuya.user.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserRequest(
        Integer id,
        @NotNull(message = "User firstname is required")
        String firstname,
        @NotNull(message = "User lastname is required")
        String lastname,
        @NotNull(message = "User Email is required")
        @Email(message = "User Email is not a valid email address")
        String username,
        @NotNull(message = "Password is required")
        String password,
        List<String> roleList
) {
}
