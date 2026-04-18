package com.example.spotter.configuration;

import com.example.spotter.application.AdminBootstrapService;
import com.example.spotter.application.command.RegisterCommand;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminBootstrapRunner implements ApplicationRunner {

    private final AdminBootstrapService adminBootstrapService;

    public AdminBootstrapRunner(AdminBootstrapService adminBootstrapService) {
        this.adminBootstrapService = adminBootstrapService;
    }

    @Value("${adminCredentials.username:admin}")
    private String username;

    @Value("${adminCredentials.password:admin}")
    private String password;

    @Override
    public void run(@NonNull ApplicationArguments args) throws Exception {
        adminBootstrapService.bootstrapIfNeeded(new RegisterCommand(username, password));
    }

}
