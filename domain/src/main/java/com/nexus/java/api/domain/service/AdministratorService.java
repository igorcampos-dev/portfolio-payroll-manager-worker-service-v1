package com.nexus.java.api.domain.service;

import com.nexus.java.api.domain.mapper.application.LoginAdministrator;
import com.nexus.java.api.domain.model.LoginAdministratorModel;

public interface AdministratorService {
    LoginAdministrator login(LoginAdministratorModel loginAdministratorModel);
}
