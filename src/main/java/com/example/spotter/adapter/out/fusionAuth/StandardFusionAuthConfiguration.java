package com.example.spotter.adapter.out.fusionAuth;

import com.example.spotter.port.out.FusionAuthPort;
import io.fusionauth.client.FusionAuthClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StandardFusionAuthConfiguration {

    @Value("${fusionauth.url:}")
    private String fusionAuthUrl;

    @Value("${fusionauth.apiKey:}")
    private String fusionAuthApiKey;

    @Bean
    FusionAuthPort fusionAuthPort() {
        if (fusionAuthUrl.isBlank() || fusionAuthApiKey.isBlank()) {
            return new DummyFusionAuthAdapter();
        }
        FusionAuthClient fusionAuthClient = new FusionAuthClient(fusionAuthUrl, fusionAuthApiKey);
        return new FusionAuthAdapter(fusionAuthClient);
    }

}
