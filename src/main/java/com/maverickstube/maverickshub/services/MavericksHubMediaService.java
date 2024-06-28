package com.maverickstube.maverickshub.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.maverickstube.maverickshub.data.model.Media;
import com.maverickstube.maverickshub.data.model.User;
import com.maverickstube.maverickshub.dto.requests.UpdateMediaRequest;
import com.maverickstube.maverickshub.dto.requests.UploadMediaFileRequest;
import com.maverickstube.maverickshub.dto.response.MediaResponse;
import com.maverickstube.maverickshub.dto.response.UpLoadMediaResponse;
import com.maverickstube.maverickshub.dto.response.UpdateMediaResponse;
import com.maverickstube.maverickshub.exception.MediaUpdateFailedException;
import com.maverickstube.maverickshub.exception.MediaUploadFailedException;
import com.maverickstube.maverickshub.repository.MediaRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class MavericksHubMediaService implements MediaService {
    private final MediaRepository mediaRepository;
    private final Cloudinary cloudinary;
    private final ModelMapper modelMapper;
    private final UserService userService;
    @Autowired
    public MavericksHubMediaService(MediaRepository mediaRepository, Cloudinary cloudinary, ModelMapper modelMapper, UserService userService) {
        this.mediaRepository = mediaRepository;
        this.cloudinary = cloudinary;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public UpLoadMediaResponse upload(UploadMediaFileRequest request){
        User uploader = userService.findUserById(request.getUserId());
        try{
            Map<?,?> response = cloudinary.uploader().upload(request.getMultipartFile().getBytes(), ObjectUtils.asMap("resource_type","auto"));
           log.info("cloudinary Upload response:: {}", response);
           String url = response.get("url").toString();
            Media media = modelMapper.map(response,Media.class);
            media.setUrl(url);
            media.setUploaderId(uploader);
            media = mediaRepository.save(media);
            return modelMapper.map(media,UpLoadMediaResponse.class);
        }
        catch(IOException error){
            throw new RuntimeException(error.getMessage());
        }
    }

    @Override
    public Media getMediaById(Long id) {
        return mediaRepository.findById(id).orElseThrow(()-> new MediaUploadFailedException("failed to upload media"));
    }

    @Override
    public UpLoadMediaResponse uploadVideo(UploadMediaFileRequest request) {
        try{
            var  response = cloudinary.uploader().upload(request.getMultipartFile().getBytes(), ObjectUtils.asMap("resource_type","video"));
            log.info("cloudinary video Upload response:: {}", response);
            String url = response.get("url").toString();
            Media media = modelMapper.map(response,Media.class);
            media.setUrl(url);
            media = mediaRepository.save(media);
            return modelMapper.map(media,UpLoadMediaResponse.class);
        }
        catch(IOException error){
            throw new RuntimeException(error.getMessage());
        }
    }

    @Override
    public UpdateMediaResponse updateMedia(UpdateMediaRequest request) {
        Media media = getMediaById(request.getMediaId());
        media.setDescription(request.getDescription());
        media.setCategory(request.getCategory());

        UpdateMediaResponse response = new UpdateMediaResponse();
        response.setId(media.getId());
        mediaRepository.save(media);
        return response;
    }
@Override
public UpdateMediaResponse updateMedia(Long mediaId, JsonPatch jsonPatch) {
        try {
            //1.get target object
            Media media = getMediaById(mediaId);
            //2.convert object from above to jsonNode (use objectMapper)
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode mediaNode = objectMapper.convertValue(media, JsonNode.class);
            //3.apply the patch to media node
            mediaNode = jsonPatch.apply(mediaNode);
            //4.convert media node back to media object
            media = objectMapper.convertValue(mediaNode, Media.class);
            media = mediaRepository.save(media);
            return modelMapper.map(media,UpdateMediaResponse.class);
        }catch (JsonPatchException e){
            throw new MediaUpdateFailedException("Failed to update media");
        }
    }

    @Override
    public List<MediaResponse> getMediaFor(Long userId){
        List<Media> media = mediaRepository.findAllMediaFor(userId);
        return media.stream()
                .map(mediaItem -> modelMapper.map(mediaItem, MediaResponse.class))
                .toList();
    }
}