package com.example.spotter.adapter.in.web;

import com.example.spotter.adapter.in.web.dto.LoginRequest;
import com.example.spotter.adapter.in.web.dto.RegisterRequest;
import com.example.spotter.port.in.AuthenticationPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication related operations")
public class AuthController {

    private final AuthenticationPort authenticationPort;

    public AuthController(AuthenticationPort authenticationPort) {
        this.authenticationPort = authenticationPort;
    }

    @Operation(summary = "Register admin", description = "Register new admin as office owner")
    @PostMapping("/register-admin")
    public String register(@RequestBody RegisterRequest registerRequest) {
        authenticationPort.registerAdmin();
        return "Register";
    }

    @Operation(summary = "Login", description = "Login to application")
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        authenticationPort.login();
        return "Login";
    }
}