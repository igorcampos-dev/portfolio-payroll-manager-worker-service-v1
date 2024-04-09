package com.nexus.java.api.application.controller.administrator;

import com.nexus.java.api.application.dto.response.AllEmployeesResponse;
import com.nexus.java.api.application.dto.response.ResponseGeneric;
import com.nexus.java.api.application.mapper.Mapper;
import com.nexus.java.api.application.util.Path;
import com.nexus.java.api.domain.service.PaycheckEmployeeService;
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
@RequestMapping(value = Path.DOMAIN, produces = MediaType.APPLICATION_JSON_VALUE)
public final class PaycheckAdministratorController {

    private final PaycheckEmployeeService paycheckEmployeeService;

    @PostMapping(path = Path.POST_ADMIN, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SuppressWarnings("unused")
    private ResponseEntity<?> sendPaycheck(@RequestParam("file") MultipartFile file, @PathVariable String userId, @PathVariable String paycheckDate){
        log.info("iniciou o processo de envio de um contraCheque para a nuvem...");
        paycheckEmployeeService.putFile(file, userId, paycheckDate);
        log.info("processo de envio de contraCheque para nuvem finalizado com sucesso.");
        return ResponseGeneric.response(HttpStatus.OK, "Arquivo enviado com sucesso");
    }

    @GetMapping(path = Path.GET_EMPLOYEES)
    @SuppressWarnings("unused")
    private ResponseEntity<List<AllEmployeesResponse>> allEmployees(){
        log.info("iniciou o processo de busca de todos os funcionários...");
        var response = Mapper.convertList(paycheckEmployeeService.findAllUsersWithBasicInfo(), AllEmployeesResponse.class);
        log.info("processo de busca de todos os funcionários finalizada com sucesso.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(path = Path.PUT_ADMIN, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SuppressWarnings("unused")
    private ResponseEntity<?> updatePaycheckOfUser(@RequestParam("file") MultipartFile file, @PathVariable String userId, @PathVariable String paycheckDate){
        log.info("iniciou o processo de atualização de um contraCheque...");
        paycheckEmployeeService.updateFile(file, userId, paycheckDate);
        log.info("processo de atualização de um contraCheque finalizado com sucesso.");
        return ResponseGeneric.response(HttpStatus.OK, "Arquivo atualizado com sucesso");
    }

    @DeleteMapping(path = Path.DELETE_ADMIN)
    @SuppressWarnings("unused")
    private ResponseEntity<?> deletePaycheckOfUser(@PathVariable String userId, @PathVariable String paycheckDate){
        log.info("iniciou o processo de exclusão de um contraCheque");
        paycheckEmployeeService.deletePaycheckById(userId, paycheckDate);
        log.info("processo de exclusão de um contraCheque finalizado com sucesso.");
        return ResponseGeneric.response(HttpStatus.OK, "Arquivo deletado com sucesso");
    }
}