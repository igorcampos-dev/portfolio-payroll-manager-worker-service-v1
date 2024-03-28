package com.nexus.java.api.infrastructure.adapter;

import com.nexus.java.api.domain.persistence.EmployeePersistence;
import com.nexus.java.api.infrastructure.validation.repository.EmployeeValidationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeAdapter implements EmployeePersistence {
    private final EmployeeValidationRepository employeeValidationRepository;
}
