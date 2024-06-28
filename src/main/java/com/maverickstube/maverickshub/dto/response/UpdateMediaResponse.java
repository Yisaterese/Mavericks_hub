package com.maverickstube.maverickshub.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maverickstube.maverickshub.data.model.Category;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
public class UpdateMediaResponse {
    private Long id;
    private String url;
    private Category category;
    private String description;
    @JsonProperty("time_created")
    private LocalDateTime timeCreated;
    @JsonProperty("time_updated")
    private LocalDateTime timeUpdated;



}
