package com.example.spotter.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class User {

    private UUID uuid;
    private UUID authProviderId;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private Attachment avatar;
    private LocalDateTime createdAt;

    public User(String email, String firstName, String lastName, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

}
