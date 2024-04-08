package com.nexus.java.api.secure.filter;

import com.nexus.security.properties.TokenProperties;
import com.nexus.security.service.filter.SecurityContextInjector;
import com.nexus.security.service.jwt.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ValidateTokenPerRequest {
    private final JwtService jwtService;
    private final AuthenticateRoutesUser authenticateRoutesUser;

    @Bean
    @SuppressWarnings("unused")
    public SecurityContextInjector securityContextInjector() {
        return token -> {
            TokenProperties login = jwtService.decode(token);
            authenticateRoutesUser.verifyAndAuthenticateUser(login);
        };
    }
}