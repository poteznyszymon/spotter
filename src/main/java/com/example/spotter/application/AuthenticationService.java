package com.example.spotter.application;

import com.example.spotter.application.command.LoginCommand;
import com.example.spotter.application.command.RegisterCommand;
import com.example.spotter.domain.User;
import com.example.spotter.port.in.AuthenticationPort;
import com.example.spotter.port.out.FusionAuthPort;
import com.example.spotter.port.out.UserRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements AuthenticationPort {

    private final FusionAuthPort fusionAuthPort;
    private final UserRepositoryPort userRepositoryPort;

    public AuthenticationService(FusionAuthPort fusionAuthPort, UserRepositoryPort userRepositoryPort) {
        this.fusionAuthPort = fusionAuthPort;
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public String registerAdmin(RegisterCommand command) {
        User user = command.toUser();
        var authResult = fusionAuthPort.register(user);
        /// TODO save user to local application database
        //userRepositoryPort.save(user);
        return authResult.token();
    }

    @Override
    public String login(LoginCommand command) {
        User user = command.toUser();
        var authResult = fusionAuthPort.login(user);
        return authResult.token();
    }

    @Override
    public void logout() {

    }

}
