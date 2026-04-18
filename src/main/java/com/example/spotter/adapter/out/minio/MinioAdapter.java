package com.example.spotter.adapter.out.minio;

import com.example.spotter.application.exception.StorageServiceException;
import com.example.spotter.domain.StorageBucket;
import com.example.spotter.port.out.FilePort;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

public class MinioAdapter implements FilePort {

    private final S3Client s3Client;
    private final MinioProperties minioProperties;

    public MinioAdapter(S3Client s3Client, MinioProperties minioProperties) {
        this.s3Client = s3Client;
        this.minioProperties = minioProperties;
    }

    @Override
    public void upload(StorageBucket bucket, String objectKey, byte[] data, String contentType) {
        try {
            var putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket.getName())
                    .key(objectKey)
                    .contentType(contentType)
                    .build();
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(data));
        } catch (S3Exception e) {
            throw new StorageServiceException("Could not upload file from S3 (AWS Error)", e);
        } catch (SdkClientException e) {
            throw new StorageServiceException("Failed to upload file from S3 (Connection Error)", e);
        }
    }

    @Override
    public void delete(StorageBucket bucket, String objectKey) {
        try {
            var deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucket.getName())
                    .key(objectKey)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);
        } catch (S3Exception e) {
            throw new StorageServiceException("Could not delete file from S3 (AWS Error)", e);
        } catch (SdkClientException e) {
            throw new StorageServiceException("Failed to delete file from S3 (Connection Error)", e);
        }
    }

    @Override
    public String getPublicUrl(StorageBucket bucket, String objectKey) {
        return minioProperties.endpoint() + "/" + bucket.getName() + "/" + objectKey;
    }

}
