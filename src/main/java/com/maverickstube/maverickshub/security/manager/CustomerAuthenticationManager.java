package com.maverickstube.maverickshub.security.manager;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomerAuthenticationManager implements AuthenticationManager {
    private final AuthenticationProvider authenticationProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Class<? extends Authentication> authenticationtype = authentication.getClass();
        if(authenticationProvider.supports(authenticationtype))
            return authenticationProvider.authenticate(authentication);
        else throw new BadCredentialsException("provide correct credentials");
    }
}
