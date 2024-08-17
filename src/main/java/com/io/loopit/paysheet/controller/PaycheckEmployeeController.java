package com.io.loopit.paysheet.controller;

import com.io.loopit.paysheet.service.PaycheckEmployeeService;
import com.nexus.aws.model.S3File;
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
@RequestMapping(value = PaycheckEmployeeController.PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PaycheckEmployeeController {

    public static final String PATH = "/v1/employee/paycheck";
    private final PaycheckEmployeeService paycheckEmployeeService;

    @GetMapping(path = "/{userId}")
    public ResponseEntity<List<S3File>> getPaychecksByUserId(@PathVariable String userId){
        log.info("iniciou o processo de busca de contraCheques de um funcionário pelo seu Id...");
        List<S3File> response = paycheckEmployeeService.getPaychecksByUserId(userId);
        log.info("processo de busca de contraCheques de um funcionário pelo seu Id finalizado com sucesso.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(path = "/{userId}/{paycheckDate}")
    public ResponseEntity<byte[]> getPaycheckByUserIdAndPaycheckDate(@PathVariable String userId, @PathVariable String paycheckDate){
        log.info("iniciou o processo de busca de um contraCheque especifico...");
        byte[] response = paycheckEmployeeService.getContentFile(userId, paycheckDate);
        log.info("processo de busca de um contraCheque especifico finalizado com sucesso.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}