package com.example.spotter.adapter.in.web.dto;

import jakarta.validation.constraints.Email;

public record LoginRequest(

        @Email(message = "Email is not valid")
        String email,
        String password

) {
}
