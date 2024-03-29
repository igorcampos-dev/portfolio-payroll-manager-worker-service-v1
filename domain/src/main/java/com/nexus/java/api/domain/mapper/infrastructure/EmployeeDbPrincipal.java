package com.nexus.java.api.domain.mapper.infrastructure;

import com.nexus.java.api.domain.mapper.infrastructure.enums.Profession;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDbPrincipal {
    private Integer code;
    private Profession profession;
    private String name;
}
