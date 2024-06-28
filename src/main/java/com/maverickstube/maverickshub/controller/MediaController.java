package com.maverickstube.maverickshub.controller;

import com.maverickstube.maverickshub.dto.requests.UploadMediaFileRequest;
import com.maverickstube.maverickshub.dto.response.UpLoadMediaResponse;
import com.maverickstube.maverickshub.services.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping(path="/api/v1/media")
public class MediaController {

    private final MediaService mediaService;
    @Autowired
    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping(consumes = {MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UpLoadMediaResponse> uploadMedia(@ModelAttribute UploadMediaFileRequest uploadMediaFileRequest){
        System.out.println(uploadMediaFileRequest);
        return ResponseEntity.status(CREATED)
                .body(mediaService.upload(uploadMediaFileRequest));
    }

    @GetMapping
    public ResponseEntity<?> getMediaForUser(@RequestParam Long userId){
     return ResponseEntity.ok(mediaService.getMediaFor(userId));
    }
}


