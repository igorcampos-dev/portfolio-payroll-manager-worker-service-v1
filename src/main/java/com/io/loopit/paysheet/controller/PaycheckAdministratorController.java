package com.io.loopit.paysheet.controller;

import com.io.loopit.paysheet.controller.dto.response.AllEmployeesResponse;
import com.io.loopit.paysheet.controller.dto.response.MessageResponse;
import com.io.loopit.paysheet.service.PaycheckEmployeeService;
import com.io.loopit.paysheet.util.ValidationUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = PaycheckAdministratorController.PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(
        name = "Rota dos administradores",
        description = "Controlador dos administradores"
)
public class PaycheckAdministratorController {

    public static final String PATH = "/v1/admin/paycheck";
    private final PaycheckEmployeeService paycheckEmployeeService;

    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponse(description = "Efetua o envio de um contraCheque de um determinado funcionário", responseCode = "200")
    @Operation(summary = "Efetua envio", description = """
            # Faz `upload` do contraCheque de um determinado funcionário
            ---
            """)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponse> sendPaycheck (
            @RequestParam("file") MultipartFile file,
            @RequestParam("employeeId") String userId,
            @RequestParam("period") String paycheckDate
    ){

        log.info("iniciou o processo de envio de um contraCheque para a nuvem...");
        ValidationUtils.validatePostAction(userId, paycheckDate, file.getContentType());
        paycheckEmployeeService.putFile(file, userId, paycheckDate);
        log.info("processo de envio de contraCheque para nuvem finalizado com sucesso.");

        return ResponseEntity.status(HttpStatus.OK).body(MessageResponse.build("Arquivo enviado com sucesso"));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponse(description = "Efetua a busca de todos os funcionários", responseCode = "200")
    @Operation(summary = "Efetua a busca", description = """
            # Faz busca de funcionários
            ---
            """)
    @GetMapping(path = "/employees")
    public ResponseEntity<List<AllEmployeesResponse>> allEmployees(
            @RequestParam(value = "page", defaultValue = "0") int page
    ){

        log.info("iniciou o processo de busca de todos os funcionários...");
        List<AllEmployeesResponse> response = paycheckEmployeeService.findAllUsersWithBasicInfo(page);
        log.info("processo de busca de todos os funcionários finalizada com sucesso.");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponse(description = "Efetua a atualização de um contraCheque de um determinado funcionário", responseCode = "200")
    @Operation(summary = "Efetua atualização", description = """
            # Faz `upload` do contraCheque de um determinado funcionário
            ---
            """)
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponse> updatePaycheckOfUser (
            @RequestParam("file") MultipartFile file,
            @RequestParam("employeeId") String userId,
            @RequestParam("period") String paycheckDate
    ){

        log.info("iniciou o processo de atualização de um contraCheque...");
        ValidationUtils.validatePutAction(userId, paycheckDate, file.getContentType());
        paycheckEmployeeService.updateFile(file, userId, paycheckDate);
        log.info("processo de atualização de um contraCheque finalizado com sucesso.");

        return ResponseEntity.status(HttpStatus.OK).body(MessageResponse.build("Arquivo atualizado com sucesso"));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponse(description = "Efetua a remoção de um contraCheque de um determinado funcionário", responseCode = "200")
    @Operation(summary = "Efetua remoção", description = """
            # Faz o `delete` do contraCheque de um determinado funcionário
            ---
            """)
    @DeleteMapping
    public ResponseEntity<MessageResponse> deletePaycheckOfUser (
            @RequestParam("employeeId") String userId,
            @RequestParam("period") String paycheckDate
    ){

        log.info("iniciou o processo de exclusão de um contraCheque");
        ValidationUtils.validateDeleteAction(userId, paycheckDate);
        paycheckEmployeeService.deletePaycheckById(userId, paycheckDate);
        log.info("processo de exclusão de um contraCheque finalizado com sucesso.");

        return ResponseEntity.status(HttpStatus.OK).body(MessageResponse.build("Arquivo deletado com sucesso"));
    }
}