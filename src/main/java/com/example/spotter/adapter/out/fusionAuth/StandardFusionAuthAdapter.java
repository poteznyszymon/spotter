package com.example.spotter.adapter.out.fusionAuth;

import com.example.spotter.application.exception.AuthProviderException;
import com.example.spotter.application.exception.InvalidCredentialsException;
import com.example.spotter.domain.AuthResult;
import com.example.spotter.domain.User;
import com.example.spotter.port.out.FusionAuthPort;
import io.fusionauth.client.FusionAuthClient;
import io.fusionauth.domain.UserRegistration;
import io.fusionauth.domain.api.LoginRequest;
import io.fusionauth.domain.api.user.RegistrationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class StandardFusionAuthAdapter implements FusionAuthPort {

    private final FusionAuthClient fusionAuthClient;
    private final String applicationId;
    private final Logger logger = LoggerFactory.getLogger(StandardFusionAuthAdapter.class);

    public StandardFusionAuthAdapter(FusionAuthClient fusionAuthClient, String applicationId) {
        this.fusionAuthClient = fusionAuthClient;
        this.applicationId = applicationId;
    }

    @Override
    public AuthResult register(User user) {
        var fusionUser = new io.fusionauth.domain.User();
        fusionUser.email = user.getEmail();
        fusionUser.password = user.getPassword();
        fusionUser.firstName = user.getFirstName();
        fusionUser.lastName = user.getLastName();

        var registration = new UserRegistration();
        registration.applicationId = UUID.fromString(applicationId);

        var registrationRequest = new RegistrationRequest(fusionUser, registration);
        var response = fusionAuthClient.register(null, registrationRequest);

        if (!response.wasSuccessful()) {
            logger.error("FusionAuth registration failed: {}", response.exception.getMessage());
            throw new AuthProviderException("FusionAuth registration failed: " + response.exception.getMessage());
        }

        var token = response.successResponse.token;
        return new AuthResult(token);
    }

    @Override
    public AuthResult login(User user) {
        var loginRequest = new LoginRequest();
        loginRequest.loginId = user.getEmail();
        loginRequest.password = user.getPassword();

        var response = fusionAuthClient.login(loginRequest);

        if (!response.wasSuccessful()) {
            logger.error("FusionAuth login failed with status: {}", response.status);
            throw new InvalidCredentialsException("Invalid email or password");
        }

        var token = response.successResponse.token;
        return new AuthResult(token);
    }

}
