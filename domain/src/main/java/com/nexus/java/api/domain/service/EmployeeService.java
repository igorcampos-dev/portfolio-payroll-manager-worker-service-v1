package com.nexus.java.api.domain.service;

import com.nexus.java.api.domain.mapper.application.LoginEmployee;
import com.nexus.java.api.domain.model.LoginEmployeeModel;
import com.nexus.java.api.domain.model.RegisterEmployeeModel;

public interface EmployeeService {
    LoginEmployee login(LoginEmployeeModel loginEmployeeModelDomain);
    void register(RegisterEmployeeModel registerEmployeeModel);
}
