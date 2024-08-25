package com.io.loopit.paysheet.controller.dto.response;

import com.io.loopit.paysheet.model.payroll.EmployeeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllEmployeesResponse {

    private String id;
    private String name;
    private String profession;

    public static AllEmployeesResponse toTypeClass(EmployeeEntity user) {
        return AllEmployeesResponse.builder()
                                   .id(user.getId())
                                   .profession(user.getProfession().name())
                                   .name(user.getName())
                                   .build();
    }
}
