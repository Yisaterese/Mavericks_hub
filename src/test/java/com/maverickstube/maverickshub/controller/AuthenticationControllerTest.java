package com.maverickstube.maverickshub.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maverickstube.maverickshub.dto.requests.LoginRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockmvc;

    @Test
    @Sql(scripts = {"/db/data.sql"})
    public void authenticateUserTest() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUsername("victormsonter@gmail.com");
        request.setPassword("password");
//        ObjectMapper mapper = new ObjectMapper();
        mockmvc.perform(post("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void authenticateUserWithInvalidCredentialsTest() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUsername("victormsonter@gmail.com");
        request.setPassword("invalid password");
        mockmvc.perform(post("/api/v1/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(request)))
                .andExpect(status().isUnauthorized())
                .andDo(print());

    }
}
