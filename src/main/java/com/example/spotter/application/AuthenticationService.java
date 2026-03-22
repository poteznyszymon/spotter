package com.example.spotter.application;

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
    public void registerAdmin() {

    }

    @Override
    public void login() {

    }

}
