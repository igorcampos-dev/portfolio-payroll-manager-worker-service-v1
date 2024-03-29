package com.nexus.java.api.domain.service.impl;

import com.nexus.java.api.domain.mapper.application.LoginEmployee;
import com.nexus.java.api.domain.mapper.infrastructure.Employee;
import com.nexus.java.api.domain.mapper.infrastructure.EmployeeDbPrincipal;
import com.nexus.java.api.domain.model.LoginEmployeeModel;
import com.nexus.java.api.domain.model.RegisterEmployeeModel;
import com.nexus.java.api.domain.persistence.EmployeePersistence;
import com.nexus.java.api.domain.service.EmployeeService;
import com.nexus.java.api.domain.service.PaycheckEmployeeService;
import com.nexus.security.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeePersistence employeePersistence;
    private final AuthenticationManager authenticationManager;
    private final PaycheckEmployeeService paycheckEmployeeService;
    private final JwtService jwtService;

    @Override
    public LoginEmployee login(LoginEmployeeModel loginEmployeeModel) {
        String token = this.jwtService.encode(this.authenticate(loginEmployeeModel));
        Employee employee = this.employeePersistence.findByCpf(loginEmployeeModel.getCpf());

        return LoginEmployee.builder()
                .id(employee.getId())
                .name(employee.getName())
                .paychecks(0)
                .profession(String.valueOf(employee.getProfession()))
                .token(token)
                .build();
    }

    @Override
    public void register(RegisterEmployeeModel registerEmployeeModel) {
        this.employeePersistence.userExistsByCpf(registerEmployeeModel.getCpf());
        EmployeeDbPrincipal employeeProperties = this.validateCpfAndNameAfterGetProperties(registerEmployeeModel.getCpf());
        this.employeePersistence.save(employeeProperties, registerEmployeeModel);
        this.paycheckEmployeeService.createFolder(employeeProperties.getName());

    }

    private UserDetails authenticate(LoginEmployeeModel loginDTO){
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.getCpf(), loginDTO.getPassword());
        return (UserDetails) this.authenticationManager.authenticate(usernamePassword).getPrincipal();
    }

    private EmployeeDbPrincipal validateCpfAndNameAfterGetProperties(String cpf) {
        return this.employeePersistence.findDescriptionByCpf(cpf);
    }
}
