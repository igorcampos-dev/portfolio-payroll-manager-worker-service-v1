package com.nexus.java.api.application.controller.administrator;

import com.nexus.java.api.application.dto.request.AdministratorLoginDto;
import com.nexus.java.api.application.dto.response.AdministratorResponse;
import com.nexus.java.api.application.mapper.Mapper;
import com.nexus.java.api.application.utils.Path;
import com.nexus.java.api.domain.service.AdministratorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Path.PATH_ADMIN, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public final class AuthenticateAdministratorController {

    private final AdministratorService administratorService;

    @PostMapping(path = "/login")
    @SuppressWarnings("unused")
    private ResponseEntity<AdministratorResponse> login(@RequestBody @Valid AdministratorLoginDto administratorLoginDto){
       var operation = administratorService.login(administratorLoginDto.toDomain());
       var response = Mapper.convert(operation, AdministratorResponse.class);
      return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}