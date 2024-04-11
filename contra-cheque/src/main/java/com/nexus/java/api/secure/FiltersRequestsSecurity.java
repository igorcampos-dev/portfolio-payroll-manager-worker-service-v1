package com.nexus.java.api.secure;

import com.nexus.java.api.application.util.Path;
import com.nexus.security.service.filter.FilterService;
import com.nexus.security.service.filter.SecurityContextInjector;
import com.nexus.security.service.routes.RoutesService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Configuration
@SuppressWarnings("unused")
public class FiltersRequestsSecurity {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, SecurityContextInjector securityContextInjector) throws Exception {
       return RoutesService.builder()
               .http(httpSecurity)
               .getPermitHasRoleUser(Path.getHasRoleUserRoutes())
               .permitAllRoutes(Path.getPermitAllRoutes())
               .permitHasRoleAdmin(Path.getHasRoleAdminRoutes())
               .filterService(new FilterService(securityContextInjector))
               .build().configure();
    }

    @Bean
    public OncePerRequestFilter customFilter(SecurityContextInjector contextInjector) {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
                new FilterService(contextInjector).doFilter(request, response, filterChain);
            }};}

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}