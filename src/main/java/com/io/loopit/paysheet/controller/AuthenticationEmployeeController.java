package com.io.loopit.paysheet.controller;

import com.io.loopit.paysheet.controller.dto.request.LoginEmployeeDto;
import com.io.loopit.paysheet.controller.dto.request.RegisterEmployeeDto;
import com.io.loopit.paysheet.controller.dto.response.LoginEmployeeResponse;
import com.io.loopit.paysheet.controller.dto.response.ResponseGeneric;
import com.io.loopit.paysheet.controller.util.Path;
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
@SuppressWarnings("unused")
@RequiredArgsConstructor
@RequestMapping(value = Path.DOMAIN, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationEmployeeController {

    private final EmployeeService employeeService;

    @PostMapping(path = Path.POST_LOGIN_EMPLOYEE)
    public ResponseEntity<LoginEmployeeResponse> authenticateEmployeeLogin(@RequestBody @Valid LoginEmployeeDto loginEmployeeDto){
        log.info("iniciou o processo de login de funcionários...");
        LoginEmployeeResponse response = employeeService.login(loginEmployeeDto);
        log.info("processo de login de funcionários finalizado com sucesso.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(path = Path.POST_VALIDATE_EMPLOYEE)
    public ResponseEntity<?> registerEmployeeAccount(@RequestBody @Valid RegisterEmployeeDto registerEmployeeDto){
        log.info("iniciou o processo de registro de um funcionário...");
        this.employeeService.register(registerEmployeeDto);
        log.info("processo de registro de um funcionário finalizado com sucesso.");
        return ResponseGeneric.response(HttpStatus.CREATED, "Usuário criado com sucesso");
    }

}