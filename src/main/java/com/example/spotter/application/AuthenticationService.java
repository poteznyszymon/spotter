package com.example.spotter.application;

import com.example.spotter.application.command.LoginCommand;
import com.example.spotter.application.command.RegisterCommand;
import com.example.spotter.application.exception.InvalidTokenException;
import com.example.spotter.configuration.DomainUserDetails;
import com.example.spotter.domain.TokenType;
import com.example.spotter.domain.User;
import com.example.spotter.port.in.AuthenticationPort;
import com.example.spotter.port.out.JwtPort;
import com.example.spotter.domain.Role;
import com.example.spotter.port.out.TokenPort;
import com.example.spotter.port.out.TokenRepositoryPort;
import com.example.spotter.port.out.UserRepositoryPort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AuthenticationService implements AuthenticationPort {

    private final JwtPort jwtPort;
    private final AuthenticationManager authenticationManager;
    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;
    private final TokenPort tokenPort;
    private final TokenRepositoryPort tokenRepositoryPort;

    public AuthenticationService(
            JwtPort jwtPort,
            AuthenticationManager authenticationManager,
            UserRepositoryPort userRepositoryPort,
            PasswordEncoder passwordEncoder,
            TokenPort tokenPort,
            TokenRepositoryPort tokenRepositoryPort) {
        this.jwtPort = jwtPort;
        this.authenticationManager = authenticationManager;
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoder = passwordEncoder;
        this.tokenPort = tokenPort;
        this.tokenRepositoryPort = tokenRepositoryPort;
    }

    @Override
    public String login(LoginCommand command) {
        var user = authenticateAndReturnUser(command.username(), command.password());
        return jwtPort.generateToken(user);
    }

    @Override
    @Transactional
    public void register(RegisterCommand command) {
        var user = command.toUser();
        var savedUser = saveUserToDatabase(command, user);
        tokenPort.sendVerificationToken(savedUser);
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

    @Override
    @Transactional
    public void activateAccount(String token) {
        var domainToken = tokenPort.validateToken(token);
        var user = userRepositoryPort.findByUuid(domainToken.getUserId()).orElseThrow(() -> new InvalidTokenException("Invalid token"));
        user.setEnabled(true);
        userRepositoryPort.save(user);
        tokenRepositoryPort.delete(domainToken);
    }

    @Override
    @Transactional
    public void resendActivationLink(String email) {
        var userOpt = userRepositoryPort.findByEmail(email);
        if (userOpt.isEmpty() || userOpt.get().isEnabled()) {
            return;
        }
        var user = userOpt.get();
        tokenRepositoryPort.deleteByUserIdAndTokenType(user.getUuid(), TokenType.ACTIVATION);
        tokenPort.sendVerificationToken(user);
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

    private User saveUserToDatabase(RegisterCommand command, User userToSave) {
        userToSave.setPassword(passwordEncoder.encode(command.password()));
        userToSave.setRole(Role.USER);
        userToSave.setEnabled(false);
        return userRepositoryPort.save(userToSave);
    }

}
