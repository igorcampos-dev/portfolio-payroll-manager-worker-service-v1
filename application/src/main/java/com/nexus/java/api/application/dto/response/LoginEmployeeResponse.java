package com.nexus.java.api.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginEmployeeResponse {
    private String id;
    private Integer paychecks;
    private String name;
    private String profession;
    private String token;
}
