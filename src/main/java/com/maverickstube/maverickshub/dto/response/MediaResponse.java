package com.maverickstube.maverickshub.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.maverickstube.maverickshub.data.model.Category;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
public class MediaResponse {
    private Long id;
    private String Url;
    private String description;
    private Category category;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timeCreated;
    private LocalDateTime timeUpdated;
}
