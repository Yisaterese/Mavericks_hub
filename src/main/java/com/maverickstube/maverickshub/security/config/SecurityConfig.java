package com.maverickstube.maverickshub.security.config;

import com.maverickstube.maverickshub.security.filter.CustomUsernamePasswordAuthenticationFilter;
import com.maverickstube.maverickshub.security.filter.CustomerAuthorizationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static org.springframework.http.HttpMethod.POST;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private AuthenticationManager authenticationManager;

    private final CustomerAuthorizationFilter customerAuthorizationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception {
        var authenticationFilter = new CustomUsernamePasswordAuthenticationFilter(authenticationManager);
        authenticationFilter.setFilterProcessesUrl("/api/v1/auth");
        return http.csrf(c->c.disable())
                    .cors(c->c.disable())
                .sessionManagement(c->c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAt(authenticationFilter,
                        BasicAuthenticationFilter.class)
                .addFilterBefore(authenticationFilter,CustomUsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(c->c.requestMatchers("/api/v1/auth").permitAll()
                                            .requestMatchers("/api/v1/media").hasAnyAuthority("USER"))
                .build();
    }


}
