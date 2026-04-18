package com.example.spotter.adapter.out.minio;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.stream.Stream;

@ConfigurationProperties(prefix = "minio")
public record MinioProperties(String endpoint, String region, String accessKey, String secretKey) {

    public boolean isConfigured() {
        return Stream.of(endpoint, region, accessKey, secretKey)
                .noneMatch(s -> s == null || s.isBlank());
    }
}
