package com.nexus.java.api.domain.service.impl;

import com.nexus.java.api.domain.exception.EmployeeNotAdminException;
import com.nexus.java.api.domain.mapper.application.LoginAdministrator;
import com.nexus.java.api.domain.mapper.infrastructure.Employee;
import com.nexus.java.api.domain.mapper.infrastructure.enums.UserRole;
import com.nexus.java.api.domain.model.LoginAdministratorModel;
import com.nexus.java.api.domain.persistence.AdministratorPersistence;
import com.nexus.java.api.domain.service.AdministratorService;
import com.nexus.security.service.jwt.JwtService;
import com.nexus.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdministratorServiceImpl implements AdministratorService {

    private final AdministratorPersistence administratorPersistence;
    private final JwtService jwtService;

    @Override
    public LoginAdministrator login(LoginAdministratorModel loginAdministratorModel) {
        Employee employee = administratorPersistence.findByCpf(loginAdministratorModel.getCpf());
        boolean condition = employee.getRole() != UserRole.ADMIN;
        Objects.throwIfTrue(condition, EMPLOYEE_NOT_ADMIN_EXCEPTION);

        return LoginAdministrator.builder()
                .name(employee.getName())
                .token(jwtService.encode(employee))
                .build();
    }

    private static final EmployeeNotAdminException EMPLOYEE_NOT_ADMIN_EXCEPTION = new EmployeeNotAdminException("O funcionário não é um administrador");
}
