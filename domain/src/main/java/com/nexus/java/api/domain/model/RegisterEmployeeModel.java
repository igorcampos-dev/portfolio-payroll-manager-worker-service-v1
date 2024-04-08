package com.nexus.java.api.domain.model;

import com.nexus.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterEmployeeModel {
    private String cpf;
    private String password;

    public String getCpf() {
        return Utils.formatCPF(cpf);
    }
}
