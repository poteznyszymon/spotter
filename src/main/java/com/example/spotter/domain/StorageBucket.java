package com.example.spotter.domain;

import lombok.Getter;

@Getter
public enum StorageBucket {
    AVATARS("avatars"),
    OFFICE("office");

    private final String name;

    StorageBucket(String name) {
        this.name = name;
    }

}
