package com.maverickstube.maverickshub.dto.requests;

import com.maverickstube.maverickshub.data.model.Category;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateMediaRequest {
    private String description;
    private Long mediaId;
    private Category category;
}
