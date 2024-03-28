package com.nexus.java.api.domain.service;

import com.nexus.java.api.domain.dto.AdministratorToDomain;
import com.nexus.java.api.domain.dto.domain.LoginAdministratorDomain;

public interface AdministratorService {
    LoginAdministratorDomain login(AdministratorToDomain administratorToDomain);
}
