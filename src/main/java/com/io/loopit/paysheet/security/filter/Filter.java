package com.io.loopit.paysheet.security.filter;

import com.io.loopit.paysheet.security.response.ErrorResponse;
import com.io.loopit.paysheet.security.util.JwtUtil;
import com.io.loopit.paysheet.util.Path;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class Filter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) {

        try {
            String jwt = request.getHeader("Authorization");
            jwtUtil.authenticate(jwt);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            ErrorResponse.getError(response, e, request);
        }

    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        return Stream.concat(
                        Path.getDefaultPublicRoutes().keySet().stream(),
                        Path.getPublicRoutes().keySet().stream()
                )
                .anyMatch(route -> request.getServletPath().contains(route));
    }

}
