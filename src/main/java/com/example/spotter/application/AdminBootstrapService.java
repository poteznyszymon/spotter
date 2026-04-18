package com.example.spotter.application;

import com.example.spotter.application.command.RegisterCommand;
import com.example.spotter.domain.Role;
import com.example.spotter.port.out.UserRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminBootstrapService {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(AdminBootstrapService.class);

    public AdminBootstrapService(UserRepositoryPort userRepositoryPort, PasswordEncoder passwordEncoder) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoder = passwordEncoder;
    }

    public void bootstrapIfNeeded(RegisterCommand command) {
        var adminExistsLocally = userRepositoryPort.existsByUsername(command.username());

        if (adminExistsLocally) {
            logger.info("Admin account already exists");
            return;
        }

        logger.info("Creating admin account");
        var user = command.toUser();
        user.setRole(Role.ADMIN);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepositoryPort.save(user);
    }

}
