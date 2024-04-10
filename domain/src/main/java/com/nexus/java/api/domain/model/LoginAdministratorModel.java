package com.nexus.java.api.domain.model;

import com.nexus.util.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginAdministratorModel {
    private String cpf;
    private String password;

    public String getCpf() {
        return SecurityUtils.formatCPF(cpf);
    }
}
