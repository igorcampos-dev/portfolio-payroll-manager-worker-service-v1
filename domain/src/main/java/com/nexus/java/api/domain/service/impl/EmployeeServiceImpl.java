package com.nexus.java.api.domain.service.impl;

import com.nexus.java.api.domain.dto.LoginEmployeeToDomain;
import com.nexus.java.api.domain.dto.RegisterEmployeeToDomain;
import com.nexus.java.api.domain.dto.domain.LoginEmployeeDomain;
import com.nexus.java.api.domain.persistence.EmployeePersistence;
import com.nexus.java.api.domain.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeePersistence employeePersistence;

    @Override
    public LoginEmployeeDomain login(LoginEmployeeToDomain loginEmployeeDomain) {
        return null;
    }

    @Override
    public void register(RegisterEmployeeToDomain registerEmployeeToDomain) {

    }
}
