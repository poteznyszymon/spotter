package com.example.spotter.port.in;

import com.example.spotter.application.command.LoginCommand;
import com.example.spotter.application.command.RegisterCommand;
import com.example.spotter.domain.User;

public interface AuthenticationPort {
    String login(LoginCommand command);
    void logout();
    User getAuthenticatedUser();
}
