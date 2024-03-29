package com.nexus.java.api.infrastructure.repository;

import com.nexus.java.api.domain.mapper.infrastructure.EmployeeDbPrincipal;

public interface PrincipalRepository {
    EmployeeDbPrincipal findDescriptionByCpf(String cpf);
}