package com.nexus.java.api.infrastructure.validation.repository.impl;

import com.nexus.java.api.infrastructure.exceptions.EmployeeNotFoundException;
import com.nexus.java.api.infrastructure.repository.EmployeeRepository;
import com.nexus.java.api.infrastructure.validation.repository.EmployeeValidationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

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

    private static final EmployeeNotFoundException USER_NOT_FOUND_EXCEPTION = new EmployeeNotFoundException("Usuário não existe.");
}
