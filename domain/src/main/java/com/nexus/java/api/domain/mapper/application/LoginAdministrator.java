package com.nexus.java.api.domain.mapper.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginAdministrator {
    private String name;
    private String token;
}
