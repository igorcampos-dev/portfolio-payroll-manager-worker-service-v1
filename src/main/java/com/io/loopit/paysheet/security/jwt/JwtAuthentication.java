package com.io.loopit.paysheet.security.jwt;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtAuthentication {
    void authenticate(String jwt);
    String encode(UserDetails userDetails);
}
