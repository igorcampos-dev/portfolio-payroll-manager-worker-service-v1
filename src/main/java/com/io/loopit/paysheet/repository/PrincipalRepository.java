package com.io.loopit.paysheet.repository;

import com.io.loopit.paysheet.model.EmployeePrincipalEntity;

public interface PrincipalRepository {
    EmployeePrincipalEntity findDescriptionByCpf(String cpf);
}