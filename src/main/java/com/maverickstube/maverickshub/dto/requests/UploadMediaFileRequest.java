package com.maverickstube.maverickshub.dto.requests;

import com.maverickstube.maverickshub.data.model.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@ToString
public class UploadMediaFileRequest {
    private Long userId;
    private MultipartFile multipartFile;
    private String description;
    private Category category;
}
