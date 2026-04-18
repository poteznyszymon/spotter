package com.example.spotter.adapter.out.minio;

import com.example.spotter.domain.StorageBucket;
import com.example.spotter.port.out.FilePort;

public class DummyMinioAdapter implements FilePort {

    @Override
    public void upload(StorageBucket bucket, String objectKey, byte[] data, String contentType) {

    }

    @Override
    public void delete(StorageBucket bucket, String objectKey) {

    }

    @Override
    public String getPublicUrl(StorageBucket bucket, String objectKey) {
        return "";
    }


}
