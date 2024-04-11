package com.nexus.java.api.application.controller.employee;

import com.nexus.java.api.application.dto.request.LoginEmployeeDto;
import com.nexus.java.api.application.dto.request.RegisterEmployeeDto;
import com.nexus.java.api.application.dto.response.LoginEmployeeResponse;
import com.nexus.java.api.application.dto.response.ResponseGeneric;
import com.nexus.java.api.application.mapper.Mapper;
import com.nexus.java.api.application.util.Path;
import com.nexus.java.api.domain.service.EmployeeService;
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
@SuppressWarnings("unused")
@RequestMapping(value = Path.DOMAIN, produces = MediaType.APPLICATION_JSON_VALUE)
public final class AuthenticationEmployeeController {

    private final EmployeeService employeeService;

    @PostMapping(path = Path.POST_LOGIN_EMPLOYEE)
    private ResponseEntity<LoginEmployeeResponse> authenticateEmployeeLogin(@RequestBody @Valid LoginEmployeeDto loginEmployeeDto){
        log.info("iniciou o processo de login de funcionários...");
        var operation = employeeService.login(loginEmployeeDto.toDomain());
        var response = Mapper.convert(operation, LoginEmployeeResponse.class);
        log.info("processo de login de funcionários finalizado com sucesso.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(path = Path.POST_VALIDATE_EMPLOYEE)
    private ResponseEntity<?> registerEmployeeAccount(@RequestBody @Valid RegisterEmployeeDto registerEmployeeDto){
        log.info("iniciou o processo de registro de um funcionário...");
        this.employeeService.register(registerEmployeeDto.toDomain());
        log.info("processo de registro de um funcionário finalizado com sucesso.");
        return ResponseGeneric.response(HttpStatus.CREATED, "Usuário criado com sucesso");
    }

}