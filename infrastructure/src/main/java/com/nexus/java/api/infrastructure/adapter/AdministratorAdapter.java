package com.nexus.java.api.infrastructure.adapter;

import com.nexus.java.api.domain.mapper.infrastructure.Employee;
import com.nexus.java.api.domain.persistence.AdministratorPersistence;
import com.nexus.java.api.infrastructure.Mapper.EmployeeMapper;
import com.nexus.java.api.infrastructure.validation.repository.EmployeeValidationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdministratorAdapter implements AdministratorPersistence {
    private final EmployeeValidationRepository employeeValidationRepository;

    @Override
    public Employee findByCpf(String cpf) {
        log.info("iniciando processo de busca de dados de um funcionário pelo cpf...");
        var employee = employeeValidationRepository.findByCpf(cpf);
        log.info("processo de busca por cpf finalizado com sucesso.");
        return EmployeeMapper.toDomain(employee);
    }
}
