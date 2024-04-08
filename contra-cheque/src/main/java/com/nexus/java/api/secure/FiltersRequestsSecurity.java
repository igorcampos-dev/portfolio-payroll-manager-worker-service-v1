package com.nexus.java.api.secure;

import com.nexus.java.api.secure.routes.RoutesLists;
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
    @SuppressWarnings("unused")
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, SecurityContextInjector securityContextInjector) throws Exception {
       return RoutesService.builder()
               .http(httpSecurity)
               .getPermitHasRoleUser(RoutesLists.getHasRoleUserRoutes())
               .permitAllRoutes(RoutesLists.getPermitAllRoutes())
               .permitHasRoleAdmin(RoutesLists.getHasRoleAdminRoutes())
               .filterService(new FilterService(securityContextInjector))
               .build().configure();
    }

    @Bean
    @SuppressWarnings("unused")
    public OncePerRequestFilter customFilter(SecurityContextInjector contextInjector) {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
                new FilterService(contextInjector).doFilter(request, response, filterChain);
            }};}

    @Bean
    @SuppressWarnings("unused")
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}