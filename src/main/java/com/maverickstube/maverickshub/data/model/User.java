package com.maverickstube.maverickshub.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique =true)
    private String email;
    private String password;
    private LocalDateTime timeCreated;
    private LocalDateTime timeUpdated;
    @OneToMany
    private List<Media> media  = new ArrayList<>();
    @PrePersist
    public void prePersist() {
        timeCreated = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        timeUpdated = LocalDateTime.now();
    }

}
