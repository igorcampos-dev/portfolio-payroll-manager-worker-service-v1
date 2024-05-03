package com.io.loopit.paysheet.controller;

import com.io.loopit.paysheet.controller.dto.request.AdministratorLoginDto;
import com.io.loopit.paysheet.controller.dto.response.AdministratorResponse;
import com.io.loopit.paysheet.controller.util.Path;
import com.io.loopit.paysheet.service.AdministratorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = Path.DOMAIN, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthenticateAdministratorController {

    private final AdministratorService administratorService;

    @PostMapping(path = Path.POST_VALIDATE_ADMIN)
    public ResponseEntity<AdministratorResponse> login(@RequestBody @Valid AdministratorLoginDto administratorLoginDto){
        log.info("iniciou o processo de login do administrador...");
        AdministratorResponse response = administratorService.login(administratorLoginDto);
        log.info("processo de login do administrador finalizado com sucesso.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}