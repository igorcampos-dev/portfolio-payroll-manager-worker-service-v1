package com.nexus.java.api.domain.model;

import com.nexus.utils.Utils;
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
        return Utils.formatCPF(cpf);
    }
}
