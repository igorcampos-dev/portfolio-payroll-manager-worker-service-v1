package com.nexus.java.api.domain.model;

import com.nexus.util.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginEmployeeModel {
    private String cpf;
    private String password;

    public String getCpf() {
        return SecurityUtils.formatCPF(cpf);
    }
}


