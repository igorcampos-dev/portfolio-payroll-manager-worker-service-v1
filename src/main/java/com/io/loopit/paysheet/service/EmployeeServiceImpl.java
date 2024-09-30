package com.io.loopit.paysheet.service;

import com.io.loopit.paysheet.controller.dto.request.LoginEmployeeDto;
import com.io.loopit.paysheet.controller.dto.request.RegisterEmployeeDto;
import com.io.loopit.paysheet.controller.dto.response.LoginEmployeeResponse;
import com.io.loopit.paysheet.controller.dto.response.RegisterEmployeeResponse;
import com.io.loopit.paysheet.model.payroll.EmployeeEntity;
import com.io.loopit.paysheet.model.rh.EmployeeRhEntity;
import com.io.loopit.paysheet.repository.payroll.EmployeeRepository;
import com.io.loopit.paysheet.repository.rh.RhRepository;
import com.io.loopit.paysheet.security.jwt.JwtAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class EmployeeServiceImpl implements EmployeeService {

    private final RhRepository rhRepository;
    private final EmployeeRepository employeeRepository;
    private final AuthenticationManager authenticationManager;
    private final PaycheckEmployeeService paycheckEmployeeService;
    private final JwtAuthentication jwtAuthentication;

    @Override
    public LoginEmployeeResponse login(LoginEmployeeDto loginEmployeeDto) {
        EmployeeEntity employee = this.employeeRepository.findByCpfOrElseThrow(loginEmployeeDto.getCpf());
        String token = this.jwtAuthentication.encode(this.authenticate(loginEmployeeDto));
        return LoginEmployeeResponse.build(employee, token);
    }

    @Override
    public RegisterEmployeeResponse register(RegisterEmployeeDto registerEmployeeDto) {
        this.employeeRepository.ifUserExistsThrow(registerEmployeeDto.getCpf());
        EmployeeRhEntity employeeRhEntity = this.rhRepository.findByCpfOrElseThrow(registerEmployeeDto.getCpf());
        EmployeeEntity employeeEntity = this.employeeRepository.save(registerEmployeeDto.toEntity(employeeRhEntity));
        return employeeEntity.toRegisterResponse();
    }

    private UserDetails authenticate(LoginEmployeeDto loginEmployeeDto){
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginEmployeeDto.getCpf(), loginEmployeeDto.getPassword());
        return (UserDetails) this.authenticationManager.authenticate(usernamePassword).getPrincipal();
    }

}
