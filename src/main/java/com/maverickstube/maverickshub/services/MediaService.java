package com.maverickstube.maverickshub.services;

import com.github.fge.jsonpatch.JsonPatch;
import com.maverickstube.maverickshub.data.model.Media;
import com.maverickstube.maverickshub.dto.requests.UpdateMediaRequest;
import com.maverickstube.maverickshub.dto.requests.UploadMediaFileRequest;
import com.maverickstube.maverickshub.dto.response.MediaResponse;
import com.maverickstube.maverickshub.dto.response.UpLoadMediaResponse;
import com.maverickstube.maverickshub.dto.response.UpdateMediaResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MediaService {
    UpLoadMediaResponse upload(UploadMediaFileRequest request);

    Media getMediaById(Long id);
    UpLoadMediaResponse uploadVideo(UploadMediaFileRequest request);

    UpdateMediaResponse updateMedia(UpdateMediaRequest request);

    UpdateMediaResponse updateMedia(Long id, JsonPatch jsonPatch);

    List<MediaResponse> getMediaFor(Long userId);
}
