package com.maverickstube.maverickshub.handlers;

import com.maverickstube.maverickshub.exception.MediaUploadFailedException;
import com.maverickstube.maverickshub.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MediaUploadFailedException.class)
    public ResponseEntity<?> handleMediaUploadFailed(MediaUploadFailedException exception){
        return ResponseEntity.status(BAD_REQUEST)
                .body(Map.of(

                        "error",exception.getMessage(),
                        "success",false));

    }
@ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException exception){
        return ResponseEntity.status(BAD_REQUEST)
                .body(Map.of(

                        "error",exception.getMessage(),
                        "success",false));


}
}
