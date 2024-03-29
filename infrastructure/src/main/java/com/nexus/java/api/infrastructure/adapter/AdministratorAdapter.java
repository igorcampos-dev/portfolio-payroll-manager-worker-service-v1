package com.nexus.java.api.infrastructure.adapter;

import com.nexus.java.api.domain.mapper.infrastructure.Employee;
import com.nexus.java.api.domain.persistence.AdministratorPersistence;
import com.nexus.java.api.infrastructure.Mapper.EmployeeMapper;
import com.nexus.java.api.infrastructure.entity.EmployeeEntity;
import com.nexus.java.api.infrastructure.validation.repository.EmployeeValidationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdministratorAdapter implements AdministratorPersistence {
    private final EmployeeValidationRepository employeeValidationRepository;

    @Override
    public Employee findByCpf(String cpf) {
        EmployeeEntity employee = employeeValidationRepository.findByCpf(cpf);
        return EmployeeMapper.toDomain(employee);
    }
}
