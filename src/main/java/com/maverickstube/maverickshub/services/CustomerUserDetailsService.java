package com.maverickstube.maverickshub.services;

import com.maverickstube.maverickshub.data.model.User;
import com.maverickstube.maverickshub.exception.UserNotFoundException;
import com.maverickstube.maverickshub.security.models.SecuredUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {
    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            User user = userService.getUserByUsername(username);
            System.out.println("reached here: " + user);
            return new SecuredUser(user);
        }catch(UserNotFoundException e){
            System.out.println("User not found");
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
