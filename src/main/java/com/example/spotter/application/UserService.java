package com.example.spotter.application;

import com.example.spotter.application.exception.StorageServiceException;
import com.example.spotter.domain.Attachment;
import com.example.spotter.domain.StorageBucket;
import com.example.spotter.port.out.FilePort;
import com.example.spotter.port.out.UserRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


@Service
public class UserService {

    private final UserRepositoryPort userRepositoryPort;
    private final FilePort filePort;

    public UserService(UserRepositoryPort userRepositoryPort, FilePort filePort) {
        this.userRepositoryPort = userRepositoryPort;
        this.filePort = filePort;
    }


    @Transactional
    public void uploadAvatar(UUID userId, MultipartFile file) {

        if (file == null ||  file.isEmpty()) {
            throw new IllegalArgumentException("File must not be empty");
        }

        // TODO change to custom error like usernotfound
        var user = userRepositoryPort.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getAvatar() != null) {
            filePort.delete(StorageBucket.AVATARS, user.getAvatar().getObjectKey());
        }

        var avatarObjectKey = UUID.randomUUID().toString();
        filePort.upload(StorageBucket.AVATARS, avatarObjectKey, readBytes(file), file.getContentType());

        var avatar = new Attachment();
        avatar.setObjectKey(avatarObjectKey);
        avatar.setBucketName(StorageBucket.AVATARS.getName());
        avatar.setOriginalName(file.getOriginalFilename());
        avatar.setContentType(file.getContentType());
        avatar.setSize(file.getSize());

        user.setAvatar(avatar);
        userRepositoryPort.save(user);
    }

    private byte[] readBytes(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new StorageServiceException("Failed to read uploaded file", e);
        }
    }

}
