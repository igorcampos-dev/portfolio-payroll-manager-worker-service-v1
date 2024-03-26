package com.nexus.java.api.application.controller;

import com.nexus.java.api.application.dto.request.AdministratorLoginDto;
import com.nexus.java.api.application.dto.response.AdministratorResponse;
import com.nexus.java.api.application.mapper.AdministratorMapper;
import com.nexus.java.api.domain.service.AdministratorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/nexus", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthAdminController {

    private final AdministratorService administratorService;
    private final AdministratorMapper administratorMapper;

    @PostMapping(path = "/login/admin")
    private ResponseEntity<AdministratorResponse> login(@RequestBody @Valid AdministratorLoginDto administratorLoginDto){
       var operation = administratorService.login(administratorMapper.toDomain(administratorLoginDto));
       var response = administratorMapper.toResponse(operation);
      return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("ok");
    }

}