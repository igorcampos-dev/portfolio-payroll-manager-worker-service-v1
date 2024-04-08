package com.nexus.java.api.application.controller.administrator;

import com.nexus.java.api.application.dto.response.AllEmployeesResponse;
import com.nexus.java.api.application.dto.response.ResponseGeneric;
import com.nexus.java.api.application.mapper.Mapper;
import com.nexus.java.api.application.util.Path;
import com.nexus.java.api.domain.service.PaycheckEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = Path.DOMAIN, produces = MediaType.APPLICATION_JSON_VALUE)
public final class PaycheckAdministratorController {

    private final PaycheckEmployeeService paycheckEmployeeService;

    @PostMapping(path = Path.POST_ADMIN, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SuppressWarnings("unused")
    private ResponseEntity<?> sendPaycheck(@RequestParam("file") MultipartFile file, @PathVariable String userId, @PathVariable String paycheckDate){
        paycheckEmployeeService.putFile(file, userId, paycheckDate);
        return ResponseGeneric.response(HttpStatus.OK, "Arquivo enviado com sucesso");
    }

    @GetMapping(path = Path.GET_EMPLOYEES)
    @SuppressWarnings("unused")
    private ResponseEntity<List<AllEmployeesResponse>> allEmployees(){
        var response = Mapper.convertList(paycheckEmployeeService.findAllUsersWithBasicInfo(), AllEmployeesResponse.class);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(path = Path.PUT_ADMIN, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SuppressWarnings("unused")
    private ResponseEntity<?> updatePaycheckOfUser(@RequestParam("file") MultipartFile file, @PathVariable String userId, @PathVariable String paycheckDate){
        paycheckEmployeeService.updateFile(file, userId, paycheckDate);
        return ResponseGeneric.response(HttpStatus.OK, "Arquivo atualizado com sucesso");
    }

    @DeleteMapping(path = Path.DELETE_ADMIN)
    @SuppressWarnings("unused")
    private ResponseEntity<?> deletePaycheckOfUser(@PathVariable String userId, @PathVariable String paycheckDate){
        paycheckEmployeeService.deletePaycheckById(userId, paycheckDate);
        return ResponseGeneric.response(HttpStatus.OK, "Arquivo deletado com sucesso");
    }
}