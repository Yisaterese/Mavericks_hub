package com.maverickstube.maverickshub.services;

import com.maverickstube.maverickshub.data.model.User;
import com.maverickstube.maverickshub.dto.requests.CreateUserRequest;
import com.maverickstube.maverickshub.dto.response.CreateUserResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    CreateUserResponse registerUser(CreateUserRequest createUserRequest);
    User findUserById(long id);
    User getUserByUsername(String username);
}
