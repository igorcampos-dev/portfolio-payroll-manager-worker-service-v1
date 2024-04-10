package com.nexus.java.api.infrastructure.validation.repository.impl;

import com.nexus.java.api.infrastructure.entity.EmployeeEntity;
import com.nexus.java.api.infrastructure.exceptions.EmployeeAlreadyException;
import com.nexus.java.api.infrastructure.exceptions.EmployeeNotFoundException;
import com.nexus.java.api.infrastructure.exceptions.EmptyEmployeeListException;
import com.nexus.java.api.infrastructure.repository.EmployeeRepository;
import com.nexus.java.api.infrastructure.validation.repository.EmployeeValidationRepository;
import com.nexus.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EmployeeValidationRepositoryImpl implements EmployeeValidationRepository {

    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails findByLoginAuth(String cpf) {
        return employeeRepository.findByCpf(cpf)
                .map( user -> new User(user.getUsername(), user.getPassword(), user.getAuthorities()))
                .orElseThrow(() -> USER_NOT_FOUND_EXCEPTION);
    }

    @Override
    public EmployeeEntity findByCpf(String cpf) {
        return employeeRepository.findByCpf(cpf).orElseThrow(() -> USER_NOT_FOUND_EXCEPTION);
    }

    @Override
    public void save(EmployeeEntity employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void userExistsByCpf(String cpf) {
        if (employeeRepository.existsByCpf(cpf)){
            throw USER_ALREADY_EXCEPTION;
        }
    }

    @Override
    public EmployeeEntity findById(String userId) {
        return employeeRepository.findById(userId).orElseThrow(() -> USER_NOT_FOUND_EXCEPTION);
    }

    @Override
    public List<EmployeeEntity> findAllUsersWithBasicInfo() {
        List<EmployeeEntity> employees = employeeRepository.findAll();
        Objects.requireNonEmpty(employees, EMPTY_EMPLOYEE_LIST_EXCEPTION );
        return employees;
    }

    private static final EmployeeNotFoundException USER_NOT_FOUND_EXCEPTION = new EmployeeNotFoundException("Funcionário não existe.");
    private static final EmployeeAlreadyException USER_ALREADY_EXCEPTION = new EmployeeAlreadyException("Funcionário já existe.");
    private static final EmptyEmployeeListException EMPTY_EMPLOYEE_LIST_EXCEPTION = new EmptyEmployeeListException("Não existe Funcionários cadastrados");
}
