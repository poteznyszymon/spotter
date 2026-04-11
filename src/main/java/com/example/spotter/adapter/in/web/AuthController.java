package com.example.spotter.adapter.in.web;

import com.example.spotter.adapter.in.web.dto.AuthResponse;
import com.example.spotter.adapter.in.web.dto.LoginRequest;
import com.example.spotter.adapter.in.web.dto.RegisterRequest;
import com.example.spotter.application.command.LoginCommand;
import com.example.spotter.application.command.RegisterCommand;
import com.example.spotter.port.in.AuthenticationPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest registerRequest) {
        var registerCommand = new RegisterCommand(
                registerRequest.email(),
                registerRequest.firstName(),
                registerRequest.lastName(),
                registerRequest.password()
        );
        var token = authenticationPort.registerAdmin(registerCommand);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @Operation(summary = "Login", description = "Login to application")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        var loginCommand = new LoginCommand(loginRequest.email(), loginRequest.password());
        var token = authenticationPort.login(loginCommand);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @Operation(summary = "Logout", description = "Logout from application")
    @PostMapping("/logout")
    public void logout() {
        authenticationPort.logout();
    }
}