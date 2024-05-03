package com.io.loopit.paysheet.controller.dto.response;

import com.io.loopit.paysheet.model.EmployeeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginEmployeeResponse {
    private String id;
    private Integer paychecks;
    private String name;
    private String profession;
    private String token;

    public static LoginEmployeeResponse buildBody(EmployeeEntity employee, String token){
        return LoginEmployeeResponse.builder()
                .id(employee.getId())
                .name(employee.getName())
                .paychecks(0)
                .profession(String.valueOf(employee.getProfession()))
                .token(token)
                .build();
    }
}
