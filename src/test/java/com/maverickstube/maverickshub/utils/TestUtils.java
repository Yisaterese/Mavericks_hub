package com.maverickstube.maverickshub.utils;

import com.maverickstube.maverickshub.data.model.Category;
import com.maverickstube.maverickshub.dto.requests.UploadMediaFileRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class TestUtils {
    public static final String TEST_IMAGE_LOCATION = "C:\\Users\\Semicolon Labs\\Downloads\\istockphoto-1447650671-1024x1024.jpg";
    public static final String TEST_VIDEO_LOCATION = "\"C:\\Users\\Semicolon Labs\\Downloads\\10 sec 2D Test animation.mp4\"";

    public static UploadMediaFileRequest buildUploadMediaFileRequest(InputStream inputStream) throws IOException {
        UploadMediaFileRequest uploadRequest = new UploadMediaFileRequest();
        MultipartFile file = new MockMultipartFile("spider man portable ", inputStream);
        uploadRequest.setCategory(Category.ACTION);
        uploadRequest.setUserId(201L);
        uploadRequest.setMultipartFile(file);
        return uploadRequest;
    }
}
