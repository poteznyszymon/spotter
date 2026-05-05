package com.example.spotter.adapter.in.web.controller;

import com.example.spotter.adapter.in.web.dto.AuthResponse;
import com.example.spotter.adapter.in.web.dto.LoginRequest;
import com.example.spotter.adapter.in.web.dto.RegisterRequest;
import com.example.spotter.adapter.in.web.dto.UserDTO;
import com.example.spotter.application.command.LoginCommand;
import com.example.spotter.application.command.RegisterCommand;
import com.example.spotter.port.in.AuthenticationPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication related operations")
public class AuthController {

    @Value("${jwt-properties.accessTokenName:token}")
    private String accessTokenName;

    @Value("${jwt-properties.expirationTimeMs:3600000}")
    private long expirationTimeMs;

    private final AuthenticationPort authenticationPort;

    public AuthController(AuthenticationPort authenticationPort) {
        this.authenticationPort = authenticationPort;
    }

    @Operation(summary = "register", description = "Register to application")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest, HttpServletResponse response) {
        var registerCommand = new RegisterCommand(registerRequest.username(), registerRequest.username(), registerRequest.firstName(), registerRequest.lastName(), registerRequest.password());
        var token = authenticationPort.register(registerCommand);
        var tokenAgeSeconds = Long.valueOf(expirationTimeMs / 1000).intValue();
        var cookie = createCookie(accessTokenName, token, tokenAgeSeconds);
        response.addCookie(cookie);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @Operation(summary = "Login", description = "Login to application")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        var loginCommand = new LoginCommand(loginRequest.username(), loginRequest.password());
        var token = authenticationPort.login(loginCommand);
        var tokenAgeSeconds = Long.valueOf(expirationTimeMs / 1000).intValue();
        var cookie = createCookie(accessTokenName, token, tokenAgeSeconds);
        response.addCookie(cookie);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @Operation(summary = "Logout", description = "Logout from application")
    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        authenticationPort.logout();
        var cookie = createCookieWithoutAge(accessTokenName);
        response.addCookie(cookie);
    }

    @Operation(summary = "Get me", description = "Get current authenticated user")
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser() {
        var user = authenticationPort.getAuthenticatedUser();
        return ResponseEntity.ok(UserDTO.fromDomain(user));
    }

    private Cookie createCookieWithoutAge(String name) {
        return createCookie(name, "", 0);
    }

    private Cookie createCookie(String name, String token, int ageSeconds) {
        var cookie = new Cookie(name, token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(ageSeconds);
        return cookie;
    }

}