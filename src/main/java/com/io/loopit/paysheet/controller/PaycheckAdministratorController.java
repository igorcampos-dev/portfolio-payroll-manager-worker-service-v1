package com.io.loopit.paysheet.controller;

import com.io.loopit.paysheet.controller.dto.response.AllEmployeesResponse;
import com.io.loopit.paysheet.controller.dto.response.ResponseGeneric;
import com.io.loopit.paysheet.util.Path;
import com.io.loopit.paysheet.service.PaycheckEmployeeService;
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
public class PaycheckAdministratorController {

    public static final String PATH = "/v1/admin/paycheck";
    private final PaycheckEmployeeService paycheckEmployeeService;

    @PostMapping(path = "/{userId}/{paycheckDate}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> sendPaycheck(@RequestParam("file") MultipartFile file, @PathVariable String userId, @PathVariable String paycheckDate){
        log.info("iniciou o processo de envio de um contraCheque para a nuvem...");
        paycheckEmployeeService.putFile(file, userId, paycheckDate);
        log.info("processo de envio de contraCheque para nuvem finalizado com sucesso.");
        return ResponseGeneric.response(HttpStatus.OK, "Arquivo enviado com sucesso");
    }

    @GetMapping(path = "/employees")
    public ResponseEntity<List<AllEmployeesResponse>> allEmployees(){
        log.info("iniciou o processo de busca de todos os funcionários...");
        List<AllEmployeesResponse> response = paycheckEmployeeService.findAllUsersWithBasicInfo();
        log.info("processo de busca de todos os funcionários finalizada com sucesso.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(path = "/{userId}/{paycheckDate}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updatePaycheckOfUser(@RequestParam("file") MultipartFile file, @PathVariable String userId, @PathVariable String paycheckDate){
        log.info("iniciou o processo de atualização de um contraCheque...");
        paycheckEmployeeService.updateFile(file, userId, paycheckDate);
        log.info("processo de atualização de um contraCheque finalizado com sucesso.");
        return ResponseGeneric.response(HttpStatus.OK, "Arquivo atualizado com sucesso");
    }

    @DeleteMapping(path = "/{userId}/{paycheckDate}")
    public ResponseEntity<?> deletePaycheckOfUser(@PathVariable String userId, @PathVariable String paycheckDate){
        log.info("iniciou o processo de exclusão de um contraCheque");
        paycheckEmployeeService.deletePaycheckById(userId, paycheckDate);
        log.info("processo de exclusão de um contraCheque finalizado com sucesso.");
        return ResponseGeneric.response(HttpStatus.OK, "Arquivo deletado com sucesso");
    }
}