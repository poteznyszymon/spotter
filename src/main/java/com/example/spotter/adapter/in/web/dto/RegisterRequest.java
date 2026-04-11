package com.example.spotter.adapter.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;

public record RegisterRequest(

        @Email(message = "Email is not valid")
        String email,
        @Min(value = 1, message = "First name cannot be empty")
        String firstName,
        @Min(value = 1, message = "Last name cannot be empty")
        String lastName,
        @Min(value = 6, message = "Password must be at least 6 characters long")
        String password

) { }
