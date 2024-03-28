package com.nexus.java.api.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllEmployeesResponse {
    private String id;
    private String name;
    private String profession;
}
