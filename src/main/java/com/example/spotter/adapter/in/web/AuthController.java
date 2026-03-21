package com.example.spotter.adapter.in.web;

import com.example.spotter.application.AuthenticationService;
import com.example.spotter.port.in.AuthenticationPort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationPort authenticationPort;

    public AuthController(AuthenticationPort authenticationPort) {
        this.authenticationPort = authenticationPort;
    }

    @PostMapping("/register-admin")
    public String register() {
        authenticationPort.registerAdmin();
        return "Register";
    }

    @PostMapping("/login")
    public String login() {
        authenticationPort.login();
        return "Login";
    }

}
