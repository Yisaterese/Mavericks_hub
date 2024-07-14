package com.maverickstube.maverickshub.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String message;
    private String token;
}
