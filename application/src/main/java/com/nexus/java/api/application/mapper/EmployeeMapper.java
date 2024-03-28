package com.nexus.java.api.application.mapper;

import com.nexus.java.api.application.dto.request.LoginEmployeeDto;
import com.nexus.java.api.application.dto.request.RegisterEmployeeDto;
import com.nexus.java.api.application.dto.response.LoginEmployeeResponse;
import com.nexus.java.api.domain.dto.LoginEmployeeToDomain;
import com.nexus.java.api.domain.dto.RegisterEmployeeToDomain;
import com.nexus.java.api.domain.dto.domain.LoginEmployeeDomain;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public LoginEmployeeToDomain employeeToDomain(LoginEmployeeDto loginEmployeeDto){
     return MODEL_MAPPER.map(loginEmployeeDto, LoginEmployeeToDomain.class);
    }
    public LoginEmployeeResponse employeeToResponse(LoginEmployeeDomain employeeDomain){
        return MODEL_MAPPER.map(employeeDomain, LoginEmployeeResponse.class);
    }

    public RegisterEmployeeToDomain  registerEmployeeToDomain(RegisterEmployeeDto registerEmployeeDto){
        return MODEL_MAPPER.map(registerEmployeeDto, RegisterEmployeeToDomain.class);
    }
}
