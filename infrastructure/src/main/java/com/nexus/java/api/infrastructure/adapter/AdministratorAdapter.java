package com.nexus.java.api.infrastructure.adapter;

import com.nexus.java.api.domain.persistence.AdministratorPersistence;
import com.nexus.java.api.infrastructure.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdministratorAdapter implements AdministratorPersistence {
    private final EmployeeRepository employeeRepository;
}
