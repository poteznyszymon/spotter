package com.example.spotter.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Attachment {

    private UUID uuid;
    private String objectKey;
    private String bucketName;
    private String originalName;
    private String contentType;
    private Long size;
    private LocalDateTime createdAt;

}
