package com.io.loopit.paysheet.security.util;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;
import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtImpl implements JwtUtil{
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;


    @Override
    public void authenticate(String jwt) {

        if (this.validToken(jwt)){
            var decodedJwt = jwtDecoder.decode(this.pureToken(jwt));

            List<String> roles = decodedJwt.getClaim("roles");

            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            var user = new UsernamePasswordAuthenticationToken(decodedJwt.getClaim("username"),null,authorities);
            SecurityContextHolder.getContext().setAuthentication(user);
        }

    }

    @Override
    public String encode(UserDetails userDetails) {

        var expiresIn = 24L * 60L * 60L;

        var scopes = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        var claims = JwtClaimsSet.builder()
                .issuer("api-auth")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(expiresIn))
                .claim("username", userDetails.getUsername())
                .claim("roles", scopes)
                .build();

        var token = JwtEncoderParameters.from(claims);
        return jwtEncoder.encode(token).getTokenValue();
    }


    private boolean validToken(String jwt) {
        try {
            Objects.requireNonNull(jwt, "Token não pode ser nulo");
            this.jwtDecoder.decode(this.pureToken(jwt));
            return true;
        }catch (Exception e){
            throw new JwtDecoderInitializationException(
                    String.format("Rota authenticada, por favor, use um token válido. causa do erro: %s", e.getMessage()),
                    e
            );
        }
    }

    private String pureToken(String jwt){
        return jwt.replace("Bearer", "").trim();
    }

}
