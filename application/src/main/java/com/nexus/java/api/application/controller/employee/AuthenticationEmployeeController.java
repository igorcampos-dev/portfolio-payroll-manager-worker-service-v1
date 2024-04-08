package com.nexus.java.api.application.controller.employee;

import com.nexus.java.api.application.dto.request.LoginEmployeeDto;
import com.nexus.java.api.application.dto.request.RegisterEmployeeDto;
import com.nexus.java.api.application.dto.response.LoginEmployeeResponse;
import com.nexus.java.api.application.dto.response.ResponseGeneric;
import com.nexus.java.api.application.mapper.Mapper;
import com.nexus.java.api.application.utils.Path;
import com.nexus.java.api.domain.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = Path.PATH_EMPLOYEES, produces = MediaType.APPLICATION_JSON_VALUE)
public final class AuthenticationEmployeeController {

    private final EmployeeService employeeService;

    @PostMapping(path = "/login")
    @SuppressWarnings("unused")
    private ResponseEntity<LoginEmployeeResponse> authenticateEmployeeLogin(@RequestBody @Valid LoginEmployeeDto loginEmployeeDto){
        var operation = employeeService.login(loginEmployeeDto.toDomain());
        var response = Mapper.convert(operation, LoginEmployeeResponse.class);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(path = "/validate")
    @SuppressWarnings("unused")
    private ResponseEntity<?> registerEmployeeAccount(@RequestBody @Valid RegisterEmployeeDto registerEmployeeDto){
        this.employeeService.register(registerEmployeeDto.toDomain());
        return ResponseGeneric.response(HttpStatus.CREATED, "Usuário criado com sucesso");
    }

}