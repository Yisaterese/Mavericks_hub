package com.maverickstube.maverickshub.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    private String email;
    private String password;
}
