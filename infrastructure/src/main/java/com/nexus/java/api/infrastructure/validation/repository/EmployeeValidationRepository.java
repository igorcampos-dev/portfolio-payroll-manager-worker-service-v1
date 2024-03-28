package com.nexus.java.api.infrastructure.validation.repository;

import org.springframework.security.core.userdetails.UserDetails;

public interface EmployeeValidationRepository {
    UserDetails findByLoginAuth(String cpf);
}
