package com.io.loopit.paysheet.controller;

import com.io.loopit.paysheet.controller.dto.request.LoginEmployeeDto;
import com.io.loopit.paysheet.controller.dto.response.RegisterEmployeeResponse;
import com.io.loopit.paysheet.controller.dto.response.LoginEmployeeResponse;
import com.io.loopit.paysheet.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = AuthenticationController.PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    public static final String PATH = "/v1/auth";
    private final EmployeeService employeeService;

    @PostMapping(path = "/login")
    public ResponseEntity<LoginEmployeeResponse> authenticateEmployeeLogin(@RequestBody @Valid LoginEmployeeDto loginEmployeeDto){
        log.info("iniciou o processo de login de funcionários...");
        LoginEmployeeResponse response = employeeService.login(loginEmployeeDto);
        log.info("processo de login de funcionários finalizado com sucesso.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<RegisterEmployeeResponse> registerEmployeeAccount(@RequestBody @Valid com.io.loopit.paysheet.controller.dto.request.RegisterEmployeeDto registerEmployeeDto){
        log.info("iniciou o processo de registro de um funcionário...");
        RegisterEmployeeResponse employee = this.employeeService.register(registerEmployeeDto);
        log.info("processo de registro de um funcionário finalizado com sucesso.");
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

}