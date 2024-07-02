package com.io.loopit.paysheet.security.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtImpl implements JwtUtil {

    private static final long EXPIRES_IN = 24L * 60L * 60L;
    private static final String ISSUER = "api-auth";

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private static final ConcurrentHashMap<String, Jwt> JWT_CACHE = new ConcurrentHashMap<>();

    @Override
    public void authenticate(String jwt) {
        System.out.println(JWT_CACHE.size());
        if (this.validToken(jwt)) {
            var decodedJwt = JWT_CACHE.computeIfAbsent(jwt, key -> jwtDecoder.decode(this.pureToken(key)));

            List<String> roles = decodedJwt.getClaim("roles");

            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            var user = new UsernamePasswordAuthenticationToken(decodedJwt.getClaim("username"), null, authorities);
            SecurityContextHolder.getContext().setAuthentication(user);
        }
    }

    @Override
    public String encode(UserDetails userDetails) {
        var scopes = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        var claims = JwtClaimsSet.builder()
                .issuer(ISSUER)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(EXPIRES_IN))
                .claim("username", userDetails.getUsername())
                .claim("roles", scopes)
                .build();

        var token = JwtEncoderParameters.from(claims);
        return jwtEncoder.encode(token).getTokenValue();
    }

    private boolean validToken(String jwt) {
        try {
            Objects.requireNonNull(jwt, "Token não pode ser nulo");
            Jwt decodedJwt = JWT_CACHE.computeIfAbsent(jwt, key -> jwtDecoder.decode(this.pureToken(key)));
            this.ifExpiredThenThrow(decodedJwt);
            return true;
        } catch (Exception e) {
            log.error("Erro na validação do token: {}", e.getMessage());
            throw new JwtDecoderInitializationException(String.format("Erro na validação do token: %s", e.getMessage()), e);
        }
    }

    private void ifExpiredThenThrow(Jwt decodedJwt) {
        if (Objects.requireNonNull(decodedJwt.getExpiresAt()).isBefore(Instant.now())) {
            throw new JwtDecoderInitializationException("Token expirado.", new Exception());
        }
    }

    private String pureToken(String jwt) {
        return jwt.trim().replace("Bearer", "");
    }

    @Scheduled(fixedRate = 1800000) //30 minutes
    @SuppressWarnings("unused")
    public void clearJwtCache() {
        log.info("Limpando cache do JWT...");
        JWT_CACHE.clear();
        log.info("Limpo!");
    }
}