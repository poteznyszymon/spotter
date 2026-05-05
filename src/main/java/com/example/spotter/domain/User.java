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
    private boolean locked;
    private boolean enabled;
    private Role role;
    private Attachment avatar;
    private LocalDateTime createdAt;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String email, String username, String firstName, String lastName, String password) {
        this.email = email;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }
}
