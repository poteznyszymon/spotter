package com.example.spotter.application;

import com.example.spotter.application.command.LoginCommand;
import com.example.spotter.application.command.RegisterCommand;
import com.example.spotter.configuration.DomainUserDetails;
import com.example.spotter.domain.User;
import com.example.spotter.port.in.AuthenticationPort;
import com.example.spotter.port.out.TokenPort;
import com.example.spotter.domain.Role;
import com.example.spotter.port.out.UserRepositoryPort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationService implements AuthenticationPort {

    private final TokenPort tokenPort;
    private final AuthenticationManager authenticationManager;
    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(
            TokenPort tokenPort,
            AuthenticationManager authenticationManager,
            UserRepositoryPort userRepositoryPort,
            PasswordEncoder passwordEncoder) {
        this.tokenPort = tokenPort;
        this.authenticationManager = authenticationManager;
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(LoginCommand command) {
        var user = authenticateAndReturnUser(command.username(), command.password());
        return tokenPort.generateToken(user);
    }

    @Override
    @Transactional
    public String register(RegisterCommand command) {
        var userToSave = command.toUser();
        saveUserToDatabase(command, userToSave);
        var user = authenticateAndReturnUser(command.username(), command.password());
        return tokenPort.generateToken(user);
    }

    @Override
    public void logout() { }

    @Override
    public User getAuthenticatedUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new RuntimeException("No authenticated user found");
        }
        var user = (DomainUserDetails) authentication.getPrincipal();
        if (user == null) {
            throw new RuntimeException("No authenticated user found");
        }
        return user.user();
    }

    private User authenticateAndReturnUser(String username, String password) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        var user = (DomainUserDetails) authentication.getPrincipal();
        if (user == null) {
            throw new RuntimeException("No authenticated user found");
        }
        return user.user();
    }

    private void saveUserToDatabase(RegisterCommand command, User userToSave) {
        userToSave.setPassword(passwordEncoder.encode(command.password()));
        userToSave.setRole(Role.USER);
        userToSave.setEnabled(true);
        userRepositoryPort.save(userToSave);
    }

}
