package com.example.spotter.port.out;

import com.example.spotter.domain.StorageBucket;

public interface FilePort {
    void upload(StorageBucket bucket, String objectKey, byte[] data, String contentType);
    void delete(StorageBucket bucket, String objectKey);
    String getPublicUrl(StorageBucket bucket, String objectKey);
}
