package com.example.spotter.application;

import com.example.spotter.application.command.LoginCommand;
import com.example.spotter.domain.User;
import com.example.spotter.port.in.AuthenticationPort;
import com.example.spotter.port.out.TokenPort;
import com.example.spotter.port.out.UserRepositoryPort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements AuthenticationPort {

    private final TokenPort tokenPort;
    private final AuthenticationManager authenticationManager;
    private final UserRepositoryPort userRepositoryPort;

    public AuthenticationService(
            TokenPort tokenPort,
            AuthenticationManager authenticationManager,
            UserRepositoryPort userRepositoryPort
    ) {
        this.tokenPort = tokenPort;
        this.authenticationManager = authenticationManager;
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public String login(LoginCommand command) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(command.username(), command.password()));
        return tokenPort.generateToken(command.username());
    }

    @Override
    public void logout() {
        /// TODO maybe later add ability to revoke tokens
    }

    @Override
    public User getAuthenticatedUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No authenticated user found");
        }

        String username = authentication.getName();
        return userRepositoryPort.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}
