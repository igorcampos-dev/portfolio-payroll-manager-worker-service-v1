package com.nexus.java.api.domain.persistence;

import com.nexus.java.api.domain.mapper.infrastructure.Employee;

public interface AdministratorPersistence {
    Employee findByCpf(String cpf);
}
