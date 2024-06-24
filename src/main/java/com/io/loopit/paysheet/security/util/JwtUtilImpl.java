package com.io.loopit.paysheet.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class JwtUtilImpl implements JwtUtil {

    @SuppressWarnings("unused")
    @Value("${spring.security.jwt.signature}")
    private String signature;

    private boolean isValidToken(String jwt) {
        try {
            this.isNull(jwt);
            DecodedJWT jwtAuth = this.decodedJWT(jwt);
            return this.hasClaims(jwtAuth);
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Rota authenticada, é necessário enviar um token válido.");
        }
    }

    private boolean hasClaims(DecodedJWT jwt){
        return jwt.getClaim("roles").asList(String.class).stream().findFirst().isPresent() &&
               !jwt.getClaim("username").as(String.class).isEmpty();
    }

    private DecodedJWT decodedJWT(String jwt){
        jwt = this.getPureToken(jwt);
        return JWT.require(Algorithm.HMAC256(signature))
                .withIssuer("api-auth")
                .build()
                .verify(jwt);
    }

    @Override
    public void authenticate(String jwt) {

        if (this.isValidToken(jwt)){
            DecodedJWT jwtAuth = this.decodedJWT(jwt);

            List<SimpleGrantedAuthority> authorities = jwtAuth.getClaim("roles").asList(String.class).stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            var user = new UsernamePasswordAuthenticationToken(jwtAuth.getClaim("username"), null, authorities);
            SecurityContextHolder.getContext().setAuthentication(user);
        }

    }

    @Override
    public String encode(UserDetails userDetails){
        return JWT.create()
                .withIssuer("api-auth")
                .withClaim("username", userDetails.getUsername())
                .withClaim("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .withExpiresAt(getExpirationDate())
                .sign(Algorithm.HMAC256(signature));
    }

    private Instant getExpirationDate(){
        return LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("-03:00"));
    }

    private String getPureToken(String jwt){
        return jwt.replace("Bearer", "").trim();
    }

    private void isNull(String jwt){
        if (jwt == null)
            throw new NullPointerException("O token está vazio.");
    }

}
