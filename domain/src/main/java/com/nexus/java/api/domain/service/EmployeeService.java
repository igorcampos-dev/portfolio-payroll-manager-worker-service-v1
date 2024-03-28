package com.nexus.java.api.domain.service;

import com.nexus.java.api.domain.dto.LoginEmployeeToDomain;
import com.nexus.java.api.domain.dto.RegisterEmployeeToDomain;
import com.nexus.java.api.domain.dto.domain.LoginEmployeeDomain;

public interface EmployeeService {
    LoginEmployeeDomain login(LoginEmployeeToDomain loginEmployeeDomain);
    void register(RegisterEmployeeToDomain registerEmployeeToDomain);
}
