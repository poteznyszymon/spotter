package com.example.spotter.adapter.out.minio;

import com.example.spotter.domain.StorageBucket;
import com.example.spotter.port.out.FilePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;

import java.net.URI;
import java.util.EnumSet;

@Configuration
@EnableConfigurationProperties(MinioProperties.class)
public class MinioConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(MinioConfiguration.class);
    private final MinioProperties minioProperties;

    public MinioConfiguration(MinioProperties minioProperties) {
        this.minioProperties = minioProperties;
    }

    @Bean
    FilePort filePort() {
        if (!minioProperties.isConfigured()) {
            logger.info("using dummy minio adapter");
            return new DummyMinioAdapter();
        }
        var s3Client = buildS3Client();
        ensureBucketsExist(s3Client);
        logger.info("Using standard minio adapter {}", minioProperties.endpoint());
        return new MinioAdapter(s3Client, minioProperties);
    }

    private void ensureBucketsExist(S3Client s3Client) {
        for (var bucket : EnumSet.allOf(StorageBucket.class)) {
            try {
                s3Client.headBucket(b -> b.bucket(bucket.getName()));
            } catch (NoSuchBucketException e) {
                s3Client.createBucket(b -> b.bucket(bucket.getName()));
                logger.info("Created bucket '{}'", bucket.getName());
            }
        }
    }

    private S3Client buildS3Client() {
        return S3Client.builder()
                .region(Region.of(minioProperties.region()))
                .endpointOverride(URI.create(minioProperties.endpoint()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(minioProperties.accessKey(), minioProperties.secretKey())
                ))
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true)
                        .build())
                .build();
    }
}

