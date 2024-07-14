package com.maverickstube.maverickshub.services;

import com.maverickstube.maverickshub.data.model.Authority;
import com.maverickstube.maverickshub.data.model.User;
import com.maverickstube.maverickshub.dto.requests.CreateUserRequest;
import com.maverickstube.maverickshub.dto.response.CreateUserResponse;
import com.maverickstube.maverickshub.exception.UserNotFoundException;
import com.maverickstube.maverickshub.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;


@Service

public class MavericksUserService implements UserService{

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
@Autowired
    public MavericksUserService(UserRepository userRepository,
                                ModelMapper mapper,
                                PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public CreateUserResponse registerUser(CreateUserRequest request){
        User user = mapper.map(request,User.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAuthorities(new HashSet<>());
        var authorities = user.getAuthorities();
        authorities.add(Authority.USER);
        user = userRepository.save(user);
        var response = mapper.map(user,CreateUserResponse.class);
        response.setMessage("success");
        return response;
    }
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public User findUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException(String.format("user with %d does not exist",id)));

}

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(()->new UserNotFoundException("user not found"));
    }


}
