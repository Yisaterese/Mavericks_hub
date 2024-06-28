package com.maverickstube.maverickshub.exception;

public class MediaUpdateFailedException extends RuntimeException{
    public MediaUpdateFailedException(String failedToUpdateMedia) {
        super(failedToUpdateMedia);
    }
}
