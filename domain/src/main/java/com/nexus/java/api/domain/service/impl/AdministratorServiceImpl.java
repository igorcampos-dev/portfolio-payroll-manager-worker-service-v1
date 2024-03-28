package com.nexus.java.api.domain.service.impl;

import com.nexus.java.api.domain.dto.AdministratorToDomain;
import com.nexus.java.api.domain.dto.domain.LoginAdministratorDomain;
import com.nexus.java.api.domain.persistence.AdministratorPersistence;
import com.nexus.java.api.domain.service.AdministratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdministratorServiceImpl implements AdministratorService {

    private final AdministratorPersistence administratorPersistence;

    @Override
    public LoginAdministratorDomain login(AdministratorToDomain administratorToDomain) {
        return new LoginAdministratorDomain();
    }
}
