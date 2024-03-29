package com.nexus.java.api.domain.mapper.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginEmployee {
    private String id;
    private Integer paychecks;
    private String name;
    private String profession;
    private String token;
}
