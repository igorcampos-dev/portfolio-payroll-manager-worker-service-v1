package com.nexus.java.api.infrastructure.adapter;

import com.nexus.java.api.domain.mapper.application.AllEmployees;
import com.nexus.java.api.domain.mapper.infrastructure.Employee;
import com.nexus.java.api.domain.mapper.infrastructure.EmployeeDbPrincipal;
import com.nexus.java.api.domain.model.RegisterEmployeeModel;
import com.nexus.java.api.domain.persistence.EmployeePersistence;
import com.nexus.java.api.infrastructure.Mapper.EmployeeMapper;
import com.nexus.java.api.infrastructure.repository.PrincipalRepository;
import com.nexus.java.api.infrastructure.validation.repository.EmployeeValidationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmployeeAdapter implements EmployeePersistence {
    private final EmployeeValidationRepository employeeValidationRepository;
    private final PrincipalRepository principalRepository;

    @Override
    public EmployeeDbPrincipal findDescriptionByCpf(String cpf) {
        return principalRepository.findDescriptionByCpf(cpf);
    }

    @Override
    public Employee findByCpf(String cpf) {
        var employee = employeeValidationRepository.findByCpf(cpf);
        return EmployeeMapper.toDomain(employee);
    }

    @Override
    public void userExistsByCpf(String cpf) {
        employeeValidationRepository.userExistsByCpf(cpf);
    }

    @Override
    public void save(EmployeeDbPrincipal employeeProperties, RegisterEmployeeModel registerEmployeeModel) {
        employeeValidationRepository.save(EmployeeMapper.PrincipalToEntity(employeeProperties, registerEmployeeModel));
    }

    @Override
    public Employee findById(String userId) {
        var employee = employeeValidationRepository.findById(userId);
        return EmployeeMapper.toDomain(employee);
    }

    @Override
    public List<AllEmployees> findAllUsersWithBasicInfo() {
        var employees = employeeValidationRepository.findAllUsersWithBasicInfo();
        return employees
                .stream()
                .map(user -> new AllEmployees(user.getId(), user.getName(), user.getProfession().name()))
                .toList();
    }
}
