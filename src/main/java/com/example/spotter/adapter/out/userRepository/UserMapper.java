package com.example.spotter.adapter.out.userRepository;

import com.example.spotter.adapter.out.persistence.models.AttachmentEntity;
import com.example.spotter.adapter.out.persistence.models.UserEntity;
import com.example.spotter.domain.Attachment;
import com.example.spotter.domain.User;

class UserMapper {

    UserEntity toEntity(User user) {
        var entity = new UserEntity();
        entity.setUuid(user.getUuid());
        entity.setUsername(user.getUsername());
        entity.setPassword(user.getPassword());
        entity.setEmail(user.getEmail());
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setRole(user.getRole());
        entity.setEnabled(user.isEnabled());
        entity.setLocked(user.isLocked());
        if (user.getAvatar() != null) {
            entity.setAvatar(toAttachmentEntity(user.getAvatar()));
        }
        return entity;
    }

    User toDomain(UserEntity entity) {
        var user = new User();
        user.setUuid(entity.getUuid());
        user.setUsername(entity.getUsername());
        user.setPassword(entity.getPassword());
        user.setEmail(entity.getEmail());
        user.setFirstName(entity.getFirstName());
        user.setLastName(entity.getLastName());
        user.setRole(entity.getRole());
        user.setEnabled(entity.isEnabled());
        user.setLocked(entity.isLocked());
        if (entity.getAvatar() != null) {
            user.setAvatar(toAttachmentDomain(entity.getAvatar()));
        }
        return user;
    }

    private AttachmentEntity toAttachmentEntity(Attachment attachment) {
        var entity = new AttachmentEntity();
        entity.setUuid(attachment.getUuid());
        entity.setObjectKey(attachment.getObjectKey());
        entity.setBucketName(attachment.getBucketName());
        entity.setOriginalName(attachment.getOriginalName());
        entity.setContentType(attachment.getContentType());
        entity.setSize(attachment.getSize());
        return entity;
    }

    private Attachment toAttachmentDomain(AttachmentEntity entity) {
        var attachment = new Attachment();
        attachment.setUuid(entity.getUuid());
        attachment.setObjectKey(entity.getObjectKey());
        attachment.setBucketName(entity.getBucketName());
        attachment.setOriginalName(entity.getOriginalName());
        attachment.setContentType(entity.getContentType());
        attachment.setSize(entity.getSize());
        attachment.setCreatedAt(entity.getCreatedAt());
        return attachment;
    }
}
