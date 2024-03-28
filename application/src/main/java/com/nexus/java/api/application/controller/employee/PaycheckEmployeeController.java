package com.nexus.java.api.application.controller.employee;

import com.nexus.aws.model.S3File;
import com.nexus.java.api.application.utils.Path;
import com.nexus.java.api.domain.service.PaycheckEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(value = Path.PATH_EMPLOYEES, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public final class PaycheckEmployeeController {

    private final PaycheckEmployeeService paycheckEmployeeService;

    @GetMapping(path = "paycheck/{userId}")
    private ResponseEntity<List<S3File>> getPaychecksByUserId(@PathVariable String userId){
        var response = paycheckEmployeeService.getPaychecksByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(response);
    }

    @GetMapping(path = "paycheck/{userId}/{paycheckDate}")
    private ResponseEntity<byte[]> getPaycheckByUserIdAndPaycheckDate(@PathVariable String userId, @PathVariable String paycheckDate){
        var response = paycheckEmployeeService.getContentFile(userId, paycheckDate);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}