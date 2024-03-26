package com.nexus.java.api.domain.service.impl;

import com.nexus.java.api.domain.dto.AdministratorToDomain;
import com.nexus.java.api.domain.dto.domain.LoginDomain;
import com.nexus.java.api.domain.persistence.AdministratorPersistence;
import com.nexus.java.api.domain.service.AdministratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdministratorServiceImpl implements AdministratorService {

    private final AdministratorPersistence administratorPersistence;


    @Override
    public LoginDomain login(AdministratorToDomain administratorToDomain) {
        System.out.println("Chegou aqui no final");
        return new LoginDomain();
    }
}
