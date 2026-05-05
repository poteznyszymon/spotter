package com.example.spotter.adapter.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @Email(message = "Email is not valid")
        String email,
        @NotBlank(message = "Username cannot be empty")
        String username,
        @NotBlank(message = "First name cannot be empty")
        String firstName,
        @NotBlank(message = "Last name cannot be empty")
        String lastName,
        @Size(min = 6, message = "Password must be at least 6 characters long")
        String password
) { }
