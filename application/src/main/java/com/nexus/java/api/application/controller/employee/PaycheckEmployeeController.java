package com.nexus.java.api.application.controller.employee;

import com.nexus.aws.model.S3File;
import com.nexus.java.api.application.util.Path;
import com.nexus.java.api.domain.service.PaycheckEmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Slf4j
@RestController
@SuppressWarnings("unused")
@RequestMapping(value = Path.DOMAIN, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public final class PaycheckEmployeeController {

    private final PaycheckEmployeeService paycheckEmployeeService;

    @GetMapping(path = Path.GET_EMPLOYEE_PAYCHECK_ID)
    private ResponseEntity<List<S3File>> getPaychecksByUserId(@PathVariable String userId){
        log.info("iniciou o processo de busca de contraCheques de um funcionário pelo seu Id...");
        var response = paycheckEmployeeService.getPaychecksByUserId(userId);
        log.info("processo de busca de contraCheques de um funcionário pelo seu Id finalizado com sucesso.");
        return ResponseEntity.status(HttpStatus.OK)
                             .body(response);
    }

    @GetMapping(path = Path.GET_EMPLOYEE_PAYCHECK_DATE)
    private ResponseEntity<byte[]> getPaycheckByUserIdAndPaycheckDate(@PathVariable String userId, @PathVariable String paycheckDate){
        log.info("iniciou o processo de busca de um contraCheque especifico...");
        var response = paycheckEmployeeService.getContentFile(userId, paycheckDate);
        log.info("processo de busca de um contraCheque especifico finalizado com sucesso.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}