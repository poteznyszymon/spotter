package com.example.spotter.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class User {

    private UUID uuid;
    private String fusionAuthId;
    private String email;
    private String firstName;
    private String lastName;
    private Attachment avatar;
    private LocalDateTime createdAt;

}
