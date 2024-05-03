package com.io.loopit.paysheet.model;

import com.io.loopit.paysheet.model.enums.Profession;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeePrincipalEntity {
    private Integer code;
    private Profession profession;
    private String name;
}
