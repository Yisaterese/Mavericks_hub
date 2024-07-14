package com.maverickstube.maverickshub.services;

import com.maverickstube.maverickshub.data.model.User;
import com.maverickstube.maverickshub.dto.requests.CreateUserRequest;
import com.maverickstube.maverickshub.dto.response.CreateUserResponse;
import com.maverickstube.maverickshub.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    public void registerUserTest(){
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("victormsonter1@gmail.com");
        createUserRequest.setPassword("password");
        CreateUserResponse response = userService.registerUser(createUserRequest);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo("success");

    }

    @Test
    @DisplayName("test that user can be retrieved by id")
    @Sql(scripts ={"/db/data.sql"})
    public void testGetByUserId(){
        User user = userService.findUserById(200L);
        assertThat(user).isNotNull();
    }
}
