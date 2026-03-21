package com.example.spotter.adapter.out.persistence.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "attachments")
public class AttachmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

}
