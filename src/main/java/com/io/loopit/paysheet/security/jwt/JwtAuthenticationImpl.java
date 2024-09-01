package com.io.loopit.paysheet.security.jwt;

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

@Log4j2
@Component
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class JwtAuthenticationImpl implements JwtAuthentication {

    private static final long EXPIRES_IN = 24L * 60L * 60L;
    private static final String ISSUER = "api-auth";

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    private static final ConcurrentHashMap<String, Jwt> JWT_CACHE = new ConcurrentHashMap<>();

    @Override
    public void authenticate(String jwt) {
        if (this.validToken(jwt)) {
            var decodedJwt = JWT_CACHE.computeIfAbsent(jwt, key -> jwtDecoder.decode(this.pureJwt(key)));
            List<String> roles = decodedJwt.getClaim("roles");
            List<SimpleGrantedAuthority> authorities = this.toRoles(roles);
            var user = new UsernamePasswordAuthenticationToken(decodedJwt.getClaim("username"), null, authorities);
            SecurityContextHolder.getContext().setAuthentication(user);
        }
    }

    @Override
    public String encode(UserDetails userDetails) {
        var scopes = this.getAuthorities(userDetails);
        var claims = this.getClaims(userDetails, scopes);
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private boolean validToken(String jwtRequest) {
        try {

            Objects.requireNonNull(jwtRequest, "Token não pode ser nulo");

            String pureJwt = this.pureJwt(jwtRequest);
            Jwt jwt = jwtDecoder.decode(pureJwt);
            Jwt decodedJwt = JWT_CACHE.computeIfAbsent(jwtRequest, key -> jwt);

            this.ifExpiredThenThrow(decodedJwt);
            return true;
        } catch (Exception e) {
            log.error("Erro na validação do token: {}", e.getMessage());
            throw new JwtDecoderInitializationException(String.format("Erro na validação do token: %s", e.getMessage()), e);
        }
    }

    private String pureJwt(String jwt) {
        return jwt.trim().replace("Bearer", "");
    }

    private List<String> getAuthorities(UserDetails userDetails){
        return userDetails.getAuthorities()
                          .stream()
                          .map(GrantedAuthority::getAuthority)
                          .collect(Collectors.toList());
    }

    private JwtClaimsSet getClaims(
            UserDetails userDetails,
            List<String> authorities
    ){
        return JwtClaimsSet.builder()
                .issuer(ISSUER)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(EXPIRES_IN))
                .claim("username", userDetails.getUsername())
                .claim("roles", authorities)
                .build();
    }

    private List<SimpleGrantedAuthority> toRoles(List<String> roles){
        return roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
    }


    private void ifExpiredThenThrow(Jwt decodedJwt) {
        if (Objects.requireNonNull(decodedJwt.getExpiresAt()).isBefore(Instant.now())) {
            throw new JwtDecoderInitializationException("Token expirado.", new Exception());
        }
    }

    @Scheduled(fixedRate = 1800000) //30 minutes
    @SuppressWarnings("unused")
    public void clearJwtCache() {
        log.info("Limpando cache do JWT...");
        JWT_CACHE.clear();
        log.info("Limpo!");
    }

}