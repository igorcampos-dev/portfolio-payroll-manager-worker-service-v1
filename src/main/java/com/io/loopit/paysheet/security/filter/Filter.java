package com.io.loopit.paysheet.security.filter;

import com.io.loopit.paysheet.security.response.ErrorResponse;
import com.io.loopit.paysheet.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

            if (jwt != null)
               authenticate(jwt);

          filterChain.doFilter(request, response);
          unauthorized(response, request);
        } catch (Exception e) {
            ErrorResponse.getError(response, e, request);
        }
    }

    private void authenticate(String jwt) {

        if (jwtUtil.isValidToken(jwt)) {
            Authentication authentication = jwtUtil.authenticate(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private void unauthorized(HttpServletResponse response, HttpServletRequest request) {
        if (response.getStatus() == HttpServletResponse.SC_UNAUTHORIZED) {
            ErrorResponse.getErrorUnauthorized(response, request);
        }
    }

}
