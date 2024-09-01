package com.io.loopit.paysheet.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class JwtProperties {

    @Value("${jwt.public.key}")
    private String publicKeyPEM;

    @Value("${jwt.private.key}")
    private String privateKeyPEM;

}
