package com.maverickstube.maverickshub.exception;

public class MediaUploadFailedException extends RuntimeException {
    public MediaUploadFailedException(String failedToUploadMedia) {
        super(failedToUploadMedia);
    }
}
