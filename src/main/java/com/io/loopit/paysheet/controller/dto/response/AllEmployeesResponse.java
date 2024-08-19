package com.io.loopit.paysheet.controller.dto.response;

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

}
