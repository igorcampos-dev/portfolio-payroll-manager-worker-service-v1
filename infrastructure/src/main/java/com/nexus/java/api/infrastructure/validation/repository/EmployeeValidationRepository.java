package com.nexus.java.api.infrastructure.validation.repository;

import com.nexus.java.api.infrastructure.entity.EmployeeEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface EmployeeValidationRepository {
    UserDetails findByLoginAuth(String cpf);
    EmployeeEntity findByCpf(String cpf);
    void save(EmployeeEntity employee);
    void userExistsByCpf(String cpf);
    EmployeeEntity findById(String userId);
    List<EmployeeEntity> findAllUsersWithBasicInfo();
}
