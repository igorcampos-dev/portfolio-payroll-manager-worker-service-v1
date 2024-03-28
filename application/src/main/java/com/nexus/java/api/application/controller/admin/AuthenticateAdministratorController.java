package com.nexus.java.api.application.controller.admin;

import com.nexus.java.api.application.dto.request.AdministratorLoginDto;
import com.nexus.java.api.application.dto.response.AdministratorResponse;
import com.nexus.java.api.application.mapper.AdministratorMapper;
import com.nexus.java.api.application.utils.Path;
import com.nexus.java.api.domain.service.AdministratorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = Path.PATH_ADMIN, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public final class AuthenticateAdministratorController {

    private final AdministratorService administratorService;
    private final AdministratorMapper administratorMapper;

    @PostMapping(path = "/login/admin")
    private ResponseEntity<AdministratorResponse> login(@RequestBody @Valid AdministratorLoginDto administratorLoginDto){
       var operation = administratorService.login(administratorMapper.administratorToDomain(administratorLoginDto));
       var response = administratorMapper.administratorToResponse(operation);
      return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}