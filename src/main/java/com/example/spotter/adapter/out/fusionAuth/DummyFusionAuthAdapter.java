package com.example.spotter.adapter.out.fusionAuth;

import com.example.spotter.domain.AuthResult;
import com.example.spotter.domain.User;
import com.example.spotter.port.out.FusionAuthPort;

public class DummyFusionAuthAdapter implements FusionAuthPort {

    @Override
    public AuthResult register(User user) {
        return new AuthResult("dummy-token");
    }

    @Override
    public AuthResult login(User user) {
        return new AuthResult("dummy-token");
    }
}
