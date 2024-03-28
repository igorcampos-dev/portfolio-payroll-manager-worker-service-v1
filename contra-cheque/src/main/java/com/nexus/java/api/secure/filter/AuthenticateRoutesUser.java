package com.nexus.java.api.secure.filter;

import com.nexus.java.api.infrastructure.repository.EmployeeRepository;
import com.nexus.security.properties.TokenProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticateRoutesUser {

    private final EmployeeRepository employeeRepository;

    public void verifyAndAuthenticateUser(TokenProperties login) {
        employeeRepository.findByCpf(login.username())
                .ifPresent(user -> {
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                });
    }
}