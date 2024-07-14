package com.maverickstube.maverickshub.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse <T>{
    private boolean status;
    private int code;
    private T data;

}
