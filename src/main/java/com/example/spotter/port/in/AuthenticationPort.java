package com.example.spotter.port.in;

import com.example.spotter.application.command.LoginCommand;
import com.example.spotter.application.command.RegisterCommand;

public interface AuthenticationPort {
    String registerAdmin(RegisterCommand command);
    String login(LoginCommand command);
    void logout();
}
