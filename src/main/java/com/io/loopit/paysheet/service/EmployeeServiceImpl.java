package com.io.loopit.paysheet.service;

import com.io.loopit.paysheet.controller.dto.request.LoginEmployeeDto;
import com.io.loopit.paysheet.controller.dto.request.RegisterEmployeeDto;
import com.io.loopit.paysheet.controller.dto.response.LoginEmployeeResponse;
import com.io.loopit.paysheet.controller.util.ValidatePasswordUtils;
import com.io.loopit.paysheet.model.EmployeeEntity;
import com.io.loopit.paysheet.model.EmployeePrincipalEntity;
import com.io.loopit.paysheet.repository.EmployeeRepository;
import com.io.loopit.paysheet.repository.PrincipalRepository;
import com.io.loopit.paysheet.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final PrincipalRepository principalRepository;
    private final EmployeeRepository employeeRepository;
    private final AuthenticationManager authenticationManager;
    private final PaycheckEmployeeService paycheckEmployeeService;
    private final JwtUtil jwtUtil;

    @Override
    public LoginEmployeeResponse login(LoginEmployeeDto loginEmployeeDto) {
        ValidatePasswordUtils.validPass(loginEmployeeDto.getPassword());
        String token = this.jwtUtil.encode(this.authenticate(loginEmployeeDto));
        EmployeeEntity employee = this.employeeRepository.findByCpfOrElseThrow(loginEmployeeDto.getCpf());
        return LoginEmployeeResponse.buildBody(employee, token);
    }

    @Override
    public void register(RegisterEmployeeDto registerEmployeeDto) {
        this.employeeRepository.ifUserExistsThrow(registerEmployeeDto.getCpf());
        EmployeePrincipalEntity employeeProperties = this.validateCpfAndNameAfterGetProperties(registerEmployeeDto.getCpf());

        ValidatePasswordUtils.validPass(registerEmployeeDto.getPassword());

        this.employeeRepository.save(registerEmployeeDto.toEntity(employeeProperties));
        this.paycheckEmployeeService.createFolder(employeeProperties.getName());
    }

    private UserDetails authenticate(LoginEmployeeDto loginDTO){
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.getCpf(), loginDTO.getPassword());
        return (UserDetails) this.authenticationManager.authenticate(usernamePassword).getPrincipal();
    }

    private EmployeePrincipalEntity validateCpfAndNameAfterGetProperties(String cpf) {
        return this.principalRepository.findDescriptionByCpf(cpf);
    }

}
