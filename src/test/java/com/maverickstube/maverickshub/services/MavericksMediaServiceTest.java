package com.maverickstube.maverickshub.services;

import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import com.maverickstube.maverickshub.data.model.Category;
import com.maverickstube.maverickshub.data.model.Media;
import com.maverickstube.maverickshub.dto.requests.UpdateMediaRequest;
import com.maverickstube.maverickshub.dto.requests.UploadMediaFileRequest;
import com.maverickstube.maverickshub.dto.response.UpdateMediaResponse;
import com.maverickstube.maverickshub.dto.response.UpLoadMediaResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.maverickstube.maverickshub.data.model.Category.HORROR;
import static com.maverickstube.maverickshub.utils.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
@AutoConfigureMockMvc
public class MavericksMediaServiceTest {
    @Autowired
    private MediaService mediaService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void uploadMediaTest(){
        Path path = Paths.get(TEST_IMAGE_LOCATION);
        try(var inputStream = Files.newInputStream(path)){
            UploadMediaFileRequest uploadRequest =  buildUploadMediaFileRequest(inputStream);
            UpLoadMediaResponse uploadResponse = mediaService.upload(uploadRequest);

            assertThat(uploadResponse).isNotNull();
            assertThat(uploadResponse.getMediaUrl()).isNotNull();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }



    @Test
    @Sql(scripts = {"/db/data.sql"})
    public void uploadVideoTest(){
        Path path = Paths.get(TEST_VIDEO_LOCATION);
        try(var inputStream = Files.newInputStream(path)){
            UploadMediaFileRequest uploadRequest =  buildUploadMediaFileRequest(inputStream);
            UpLoadMediaResponse uploadResponse = mediaService.upload(uploadRequest);

            assertThat(uploadResponse).isNotNull();
            assertThat(uploadResponse.getMediaUrl()).isNotNull();
        }catch(IOException e){
            throw new RuntimeException(e);
        }

    }

    @Test
    @Sql(scripts ={"/db/data.sql"})
    public void getMediaByIdTest(){
            Media media = mediaService.getMediaById(100L);
            assertThat(media).isNotNull();
            assertThat(media.getId()).isNotNull();
    }
    @Test
    @Sql(scripts ={"/db/data.sql"})
    public void updateMediaTest(){
        UpdateMediaRequest updateMediaRequest = new UpdateMediaRequest();
        updateMediaRequest.setMediaId(100L);
        updateMediaRequest.setCategory(HORROR);
        updateMediaRequest.setDescription("terrifying");
        UpdateMediaResponse updateMediaResponse = new UpdateMediaResponse();
        Media media = mediaService.getMediaById(updateMediaResponse.getId());

        assertThat(media).isNotNull();
        assertThat(media.getId()).isNotNull();
        assertEquals("terrifying", media.getDescription());

    }
    @Test
    @DisplayName("test update media files")
    public void updateMediaTest1() throws JsonPointerException {
        Category category = mediaService.getMediaById(100L).getCategory();
        assertThat(category).isNotEqualTo(HORROR);
        List<JsonPatchOperation> operation = List.of(new ReplaceOperation(new JsonPointer("/category"), new TextNode(HORROR.name())));
        JsonPatch updateMediaRequest = new JsonPatch(operation);
        UpdateMediaResponse response = mediaService.updateMedia(100L,
                updateMediaRequest);
        assertThat(response).isNotNull();
        category = mediaService.getMediaById(100L).getCategory();
        assertThat(category).isNotEqualTo(HORROR);
    }


    @Test
    public void getMediaForUserTest() {
        try {
            mockMvc.perform(get("/api/v1/media?userId=200")
                            .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().is2xxSuccessful())
                    .andDo(print());
        }catch (Exception e) {
            assertThat(e).isNotNull();
        }

    }

}