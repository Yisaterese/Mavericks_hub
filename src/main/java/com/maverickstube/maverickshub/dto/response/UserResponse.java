package com.maverickstube.maverickshub.dto.response;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class UserResponse {
    private Long id;
    private String email;
//    @JsonSerialize(using= LocalDateTimeSerializer.class) private
}
