package com.io.loopit.paysheet.controller;

import com.io.loopit.paysheet.controller.dto.response.Base64Response;
import com.io.loopit.paysheet.service.PaycheckEmployeeService;
import com.nexus.aws.model.S3File;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = PaycheckEmployeeController.PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(
        name = "Rota dos funcionários",
        description = "Controlador dos funcionários"
)
public class PaycheckEmployeeController {

    public static final String PATH = "/v1/employee/paycheck";
    private final PaycheckEmployeeService paycheckEmployeeService;

    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponse(description = "Efetua a busca de informações básicas dos contraaCheques de um determinado funcionário", responseCode = "200")
    @Operation(summary = "Efetua busca", description = """
            # Faz a busca de informações de contraCheques
            ---
            """)
    @GetMapping(path = "/info-basics")
    public ResponseEntity<List<S3File>> getPaychecksByUserId(
            @RequestParam("employeeId") String userId
    ){
        log.info("iniciou o processo de busca de contraCheques de um funcionário pelo seu Id...");
        List<S3File> response = paycheckEmployeeService.getPaychecksByUserId(userId);
        log.info("processo de busca de contraCheques de um funcionário pelo seu Id finalizado com sucesso.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponse(description = "Efetua a busca de um contraaCheque de um determinado funcionário", responseCode = "200")
    @Operation(summary = "Efetua busca", description = """
            # Faz a busca de um contraCheque
            ---
            """)
    @GetMapping
    public ResponseEntity<Base64Response> getPaycheckByUserIdAndPaycheckDate(
            @RequestParam("employeeId") String userId,
            @RequestParam("period") String paycheckDate
    ){
        log.info("iniciou o processo de busca de um contraCheque especifico...");
        String response = paycheckEmployeeService.getContentFile(userId, paycheckDate);
        var responseRequest = Base64Response.buildBase64(response);
        log.info("processo de busca de um contraCheque especifico finalizado com sucesso.");
        return ResponseEntity.status(HttpStatus.OK).body(responseRequest);
    }

}