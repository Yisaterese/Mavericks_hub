package com.maverickstube.maverickshub.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maverickstube.maverickshub.dto.requests.LoginRequest;
import com.maverickstube.maverickshub.dto.response.BaseResponse;
import com.maverickstube.maverickshub.dto.response.LoginResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Collection;

@AllArgsConstructor
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
        try{
            //1.Retrieve authentication credentials from the request    body
            InputStream requestBodyStream = request.getInputStream();
           //2. Convert the Json data from 1 to java object (LoginRequest)
            LoginRequest loginRequest = objectMapper.readValue(requestBodyStream,LoginRequest.class);
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();
            //3. Create an authentication object that is not yet authenticated
            Authentication authentication = new UsernamePasswordAuthenticationToken(username,password);
           //4a. pass the unauthenticated Authentication object to the AuthenticationManager
            //4b. get back the Authentication result from the AuthenticationManager
            Authentication authenticationResult = authenticationManager.authenticate(authentication);
            //5. put the Authentication result back in the authenticated
            SecurityContextHolder.getContext().setAuthentication(authenticationResult);
            return authenticationResult;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        final String token = generateAccessToken(authResult);
        System.out.println(token);
        LoginResponse loginResponse = new LoginResponse();
       loginResponse.setToken(token);
       loginResponse.setMessage("successful authentication");
       BaseResponse<LoginResponse> authenticationResponse = new BaseResponse<>();
       authenticationResponse.setCode(HttpStatus.OK.value());
       authenticationResponse.setData(loginResponse);
       authenticationResponse.setStatus(true);
       response.getOutputStream().write(objectMapper.writeValueAsBytes(authenticationResponse));
       response.flushBuffer();
       chain.doFilter(request,response);

    }

    private static String generateAccessToken(Authentication authResult) {
        String token = JWT.create()
                 .withIssuer("mavericks_hub")
                 .withArrayClaim("roles", getClamsForm(authResult.getAuthorities()))
                 .withExpiresAt(Instant.now().plusSeconds(24 * 60 * 60))
                 .sign(Algorithm.HMAC512("secret"));
        return token;
    }

    private static  String[] getClamsForm(Collection<? extends GrantedAuthority> authorities) {
       return authorities.stream()
               .map((grantedAuthority) -> grantedAuthority.getAuthority())
                .toArray(String[]::new);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException exception) throws IOException, ServletException {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMessage(exception.getMessage());
        BaseResponse<LoginResponse> baseResponse = new BaseResponse<>();
        baseResponse.setData(loginResponse);
        baseResponse.setStatus(false);
        baseResponse.setCode(HttpStatus.UNAUTHORIZED.value());

        response.getOutputStream()
                .write(objectMapper
                        .writeValueAsBytes(baseResponse));
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.flushBuffer();


        //        super.unsuccessfulAuthentication(request, response, failed);
    }

}
