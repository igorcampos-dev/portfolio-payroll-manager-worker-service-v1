package com.io.loopit.paysheet.security.filter;

import com.io.loopit.paysheet.security.response.ErrorResponse;
import com.io.loopit.paysheet.security.util.JwtUtil;
import com.io.loopit.paysheet.util.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class Filter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(@Nullable HttpServletRequest request,
                                    @Nullable HttpServletResponse response,
                                    @Nullable FilterChain filterChain) {

        assert request != null;
        assert filterChain != null;
        assert response != null;

        try {
            String jwt = request.getHeader("Authorization");
            jwtUtil.authenticate(jwt);
          filterChain.doFilter(request, response);
        } catch (Exception e) {
            ErrorResponse.getError(response, e, request);
        }
    }

    @Override
    protected boolean shouldNotFilter(@Nullable HttpServletRequest request) {
        assert request != null;
        return Path.getPermitAllRoutes()
                   .stream()
                   .anyMatch(route -> request.getServletPath().contains(route.getRoute()));
    }


}
