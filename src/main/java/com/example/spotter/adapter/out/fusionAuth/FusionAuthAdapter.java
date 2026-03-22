package com.example.spotter.adapter.out.fusionAuth;

import com.example.spotter.port.out.FusionAuthPort;
import io.fusionauth.client.FusionAuthClient;
import io.fusionauth.domain.User;
import io.fusionauth.domain.UserRegistration;
import io.fusionauth.domain.api.user.RegistrationRequest;

public class FusionAuthAdapter implements FusionAuthPort {

    private final FusionAuthClient fusionAuthClient;

    public FusionAuthAdapter(FusionAuthClient fusionAuthClient) {
        this.fusionAuthClient = fusionAuthClient;
    }

    @Override
    public void register() {
        var user = new User();
        var registration = new UserRegistration();
        var registrationRequest = new RegistrationRequest(user, registration);
        var response = fusionAuthClient.register(null, registrationRequest);

        if (response.wasSuccessful()) {
            // THROW CUSTOM EXCEPTION
        }

    }

    @Override
    public void login() {

    }
}
