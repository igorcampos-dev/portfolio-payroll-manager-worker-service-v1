package com.nexus.java.api.domain.dto.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllEmployeesDomain {
    private String id;
    private String name;
    private String profession;
}
