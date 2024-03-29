package com.nexus.java.api.infrastructure.repository;

import com.nexus.java.api.infrastructure.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Service> {
    Optional<EmployeeEntity> findByCpf(String cpf);
    Optional<EmployeeEntity> findById(String userId);
    boolean existsByCpf(String cpf);
}
