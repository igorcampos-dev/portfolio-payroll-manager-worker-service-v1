package com.io.loopit.paysheet.controller.dto.response;

import com.io.loopit.paysheet.model.enums.Profession;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterEmployeeResponse {

    private String name;
    private Profession profession;
    private String cpf;

}
