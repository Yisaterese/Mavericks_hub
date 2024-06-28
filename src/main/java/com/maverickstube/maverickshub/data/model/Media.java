package com.maverickstube.maverickshub.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static java.time.LocalDateTime.now;

@Entity
@Getter
@Setter
@Table(name = "media")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("media_url")
    private String url;
    @JsonProperty("media_description")
    private String description;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Setter(AccessLevel.NONE)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timeCreated;
    @Setter(AccessLevel.NONE)
    private LocalDateTime timeUpdated;
    @ManyToOne
    private User uploaderId;
    @PrePersist
    private void setTimeCreated(){
        this.timeCreated = now();
    }

    @PreUpdate
    private void setTimeUpdated(){
        this.timeUpdated = now();
    }
}
