package com.nexus.java.api.infrastructure.connect;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Builder
@Component
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "enterprise.credential")
public class ConnectProperties {
    private String url;
    private String username;
    private String password;
}
