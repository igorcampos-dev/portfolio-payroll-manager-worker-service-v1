package com.io.loopit.paysheet.repository;

import com.io.loopit.paysheet.model.EmployeePrincipalEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PrincipalRepository {
    EmployeePrincipalEntity findDescriptionByCpf(String cpf);
}