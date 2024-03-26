package com.nexus.java.api.domain.service;


import com.nexus.java.api.domain.dto.AdministratorToDomain;
import com.nexus.java.api.domain.dto.domain.LoginDomain;

public interface AdministratorService {

    LoginDomain login(AdministratorToDomain administratorToDomain);
}
