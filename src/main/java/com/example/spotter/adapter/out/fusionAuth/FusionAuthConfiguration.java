package com.example.spotter.adapter.out.fusionAuth;

import com.example.spotter.port.out.FusionAuthPort;
import io.fusionauth.client.FusionAuthClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

@Configuration
public class FusionAuthConfiguration {

    @Value("${fusionAuth.url:}")
    private String fusionAuthUrl;

    @Value("${fusionAuth.apiKey:}")
    private String fusionAuthApiKey;

    @Value("${fusionAuth.applicationId:}")
    private String applicationId;

    private final Logger logger = Logger.getLogger(FusionAuthConfiguration.class.getName());

    @Bean
    FusionAuthPort fusionAuthPort() {
        if (fusionAuthUrl.isBlank() || fusionAuthApiKey.isBlank() || applicationId.isBlank()) {
            logger.info("Using dummy FusionAuthAdapter");
            return new DummyFusionAuthAdapter();
        }
        var fusionAuthClient = new FusionAuthClient(fusionAuthApiKey, fusionAuthUrl);
        logger.info("Using standard FusionAuthAdapter");
        return new StandardFusionAuthAdapter(fusionAuthClient, applicationId);
    }

}
