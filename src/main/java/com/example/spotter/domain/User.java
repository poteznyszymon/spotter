package com.example.spotter.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class User {
    private UUID uuid;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private Role role;
    private Attachment avatar;
    private LocalDateTime createdAt;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
