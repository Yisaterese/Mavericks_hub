package com.maverickstube.maverickshub.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.annotations.processing.SQL;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.maverickstube.maverickshub.utils.TestUtils.TEST_IMAGE_LOCATION;
import static com.maverickstube.maverickshub.utils.TestUtils.TEST_VIDEO_LOCATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"/db/data.sql"})

public class MediaControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void testUploadMedia() {
        ObjectMapper mapper = new ObjectMapper();
        String token = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJtYXZlcmlja3NfaHViIiwicm9sZXMiOlsiVVNFUiJdLCJleHAiOjE3MTk0OTY3NzV9.pFTlogUiuscaWyRT1SMK83u6wYuAYQx6gLtPdiyHi3lGanSYwPr1stbXagwNQzdcTNO-B7tgY_YkCj3uWH8qNA";
        try (InputStream inputStream = Files.newInputStream(Path.of(TEST_IMAGE_LOCATION))) {
            MultipartFile file = new MockMultipartFile("multipartFile", inputStream);
            mockMvc.perform(multipart("/api/v1/media")
                            .file(file.getName(), file.getBytes())
                            .part(new MockPart("userId", "202".getBytes()))
                            .part(new MockPart("description", "test description".getBytes()))
                            .part(new MockPart("category", "ACTION".getBytes()))
                            .header("Authorization", "Bearer " + token)
                            .contentType(MediaType.MULTIPART_FORM_DATA))
                    .andExpect(status().isCreated())
                    .andDo(print());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
        public void testUploadVideoMedia(){
            ObjectMapper mapper = new ObjectMapper();
            String token = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJtYXZlcmlja3NfaHViIiwicm9sZXMiOlsiVVNFUiJdLCJleHAiOjE3MTk0OTY3NzV9.pFTlogUiuscaWyRT1SMK83u6wYuAYQx6gLtPdiyHi3lGanSYwPr1stbXagwNQzdcTNO-B7tgY_YkCj3uWH8qNA";
            try(InputStream inputStream = Files.newInputStream(Path.of(TEST_VIDEO_LOCATION))){
                MultipartFile file = new MockMultipartFile("multipartFile", inputStream);
                mockMvc.perform(multipart("/api/v1/media")
                                .file(file.getName(), file.getBytes())
                                .part(new MockPart("userId","202" .getBytes()))
                                .part(new MockPart("description", "test description" .getBytes()))
                                .part(new MockPart("category", "ACTION" .getBytes()))
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.MULTIPART_FORM_DATA))
                        .andExpect(status().isCreated())
                        .andDo(print());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }
}
