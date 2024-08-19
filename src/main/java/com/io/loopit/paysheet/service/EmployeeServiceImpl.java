package com.io.loopit.paysheet.service;

import com.io.loopit.paysheet.controller.dto.request.LoginEmployeeDto;
import com.io.loopit.paysheet.controller.dto.request.RegisterEmployeeDto;
import com.io.loopit.paysheet.controller.dto.response.LoginEmployeeResponse;
import com.io.loopit.paysheet.controller.dto.response.RegisterEmployeeResponse;
import com.io.loopit.paysheet.model.payroll.EmployeeEntity;
import com.io.loopit.paysheet.model.rh.EmployeeRhEntity;
import com.io.loopit.paysheet.repository.payroll.EmployeeRepository;
import com.io.loopit.paysheet.repository.rh.RhRepository;
import com.io.loopit.paysheet.security.util.JwtUtil;
import com.io.loopit.paysheet.util.ValidationsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class EmployeeServiceImpl implements EmployeeService {

    private final ValidationsUtils validationsUtils;
    private final RhRepository rhRepository;
    private final EmployeeRepository employeeRepository;
    private final AuthenticationManager authenticationManager;
    private final PaycheckEmployeeService paycheckEmployeeService;
    private final JwtUtil jwtUtil;

    @Override
    public LoginEmployeeResponse login(LoginEmployeeDto loginEmployeeDto) {
        validationsUtils.validatePassword(loginEmployeeDto.getPassword());
        EmployeeEntity employee = this.employeeRepository.findByCpfOrElseThrow(loginEmployeeDto.getCpf());
        String token = this.jwtUtil.encode(this.authenticate(loginEmployeeDto));
        return LoginEmployeeResponse.build(employee, token);
    }

    @Override
    public RegisterEmployeeResponse register(RegisterEmployeeDto registerEmployeeDto) {
        validationsUtils.validatePassword(registerEmployeeDto.getPassword());
        this.employeeRepository.ifUserExistsThrow(registerEmployeeDto.getCpf());
        EmployeeRhEntity entity = this.rhRepository.findDescriptionByCpf(registerEmployeeDto.getCpf());
        EmployeeEntity employee = this.employeeRepository.save(registerEmployeeDto.toEntity(entity));
        return employee.toRegisterResponse();
    }

    private UserDetails authenticate(LoginEmployeeDto loginDTO){
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.getCpf(), loginDTO.getPassword());
        return (UserDetails) this.authenticationManager.authenticate(usernamePassword).getPrincipal();
    }

}
