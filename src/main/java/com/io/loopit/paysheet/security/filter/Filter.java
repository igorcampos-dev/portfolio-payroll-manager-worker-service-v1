package com.io.loopit.paysheet.security.filter;

import com.io.loopit.paysheet.security.response.FilterResponseError;
import com.io.loopit.paysheet.security.jwt.JwtAuthentication;
import com.io.loopit.paysheet.security.routes.RoutesAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class Filter extends OncePerRequestFilter {

    private final JwtAuthentication jwtAuthentication;
    private final RoutesAuthentication routesAuthentication;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) {
        try {

            String jwt = request.getHeader("Authorization");
            jwtAuthentication.authenticate(jwt);
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            FilterResponseError
                    .builder()
                    .message(e.getMessage())
                    .path(request.getRequestURI())
                    .timestamp(LocalDateTime.now())
                    .build()
                    .getError(response);
        }

    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        String path = request.getServletPath();
        return path.contains("/v1/auth/") ||
               routesAuthentication.getAllPublicRoutes().contains(path);
    }

}
