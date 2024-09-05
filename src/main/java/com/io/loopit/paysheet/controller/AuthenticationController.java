package com.io.loopit.paysheet.controller;

import com.io.loopit.paysheet.controller.dto.request.LoginEmployeeDto;
import com.io.loopit.paysheet.controller.dto.request.RegisterEmployeeDto;
import com.io.loopit.paysheet.controller.dto.response.RegisterEmployeeResponse;
import com.io.loopit.paysheet.controller.dto.response.LoginEmployeeResponse;
import com.io.loopit.paysheet.service.EmployeeService;
import com.io.loopit.paysheet.util.ValidationUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
        name = "Login e registro do administrador e funcionários",
        description = "Controlador para authenticação"
)
public class AuthenticationController {

    public static final String PATH = "/v1/auth";
    private final EmployeeService employeeService;

    @ApiResponse(description = "Efetua login de administradores e funcionários", responseCode = "200")
    @Operation(summary = "Efetua login", description = """
            # Faz login no projeto e retorna token de authenticação
              e outras informações importantes
            ---
           
            """)
    @PostMapping(path = "/login")
    public ResponseEntity<LoginEmployeeResponse> authenticateEmployeeLogin (
            @RequestBody @Valid LoginEmployeeDto loginEmployeeDto
    ){

        log.info("iniciou o processo de login de funcionários...");
        ValidationUtils.validatePassword(loginEmployeeDto.getPassword());
        LoginEmployeeResponse response = employeeService.login(loginEmployeeDto);
        log.info("processo de login de funcionários finalizado com sucesso.");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiResponse(description = "Efetua registro de funcionários", responseCode = "200")
    @Operation(summary = "Efetua registro", description = """
            # Faz registro no projeto
            ---
           
            """)
    @PostMapping(path = "/register")
    public ResponseEntity<RegisterEmployeeResponse> registerEmployeeAccount (
            @RequestBody @Valid RegisterEmployeeDto registerEmployeeDto
    ){

        log.info("iniciou o processo de registro de um funcionário...");
        ValidationUtils.validatePassword(registerEmployeeDto.getPassword());
        RegisterEmployeeResponse employee = this.employeeService.register(registerEmployeeDto);
        log.info("processo de registro de um funcionário finalizado com sucesso.");

        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

}